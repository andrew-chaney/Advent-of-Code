from itertools import combinations, permutations

class Player:
    def __init__(self, hp: int, dmg: int, rmr: int):
        self.hp = hp
        self.dmg = dmg
        self.rmr = rmr
        self.defaultHP = hp
        self.defaultDMG = dmg
        self.defaultRMR = rmr

    def getHP(self) -> int:
        return self.hp
    
    def getDMG(self) -> int:
        return self.dmg

    def getRMR(self) -> int:
        return self.rmr
    
    def loseHP(self, loss: int):
        self.hp -= loss

    def addDMG(self, points: int):
        self.dmg += points

    def addRMR(self, points: int):
        self.rmr += points

    def reset(self):
        self.hp = self.defaultHP
        self.dmg = self.defaultDMG
        self.rmr = self.defaultRMR

def battle(boss, player) -> bool:
    while True:
        boss.loseHP(max(1, (player.getDMG() - boss.getRMR())))
        if boss.getHP() <= 0:
            return True
        player.loseHP(max(1, (boss.getDMG() - player.getRMR())))
        if player.getHP() <= 0:
            return False

def findMinMax(boss, player, weps: dict, armor: dict, rings: dict, pt2=False) -> int:
    if pt2 == False:
        min_cost = 0
    else:
        max_cost = 0
    for w in combinations(weps.keys(), 1):
        for a in combinations(armor.keys(), 1):
            for rs in combinations(rings.keys(), 2):
                boss.reset()
                player.reset()
                cost = 0
                player.addDMG(weps[w[0]][1])
                cost += weps[w[0]][0]
                player.addRMR(armor[a[0]][1])
                cost += armor[a[0]][0]
                for r in rs:
                    player.addDMG(rings[r][1])
                    player.addRMR(rings[r][2])
                    cost += rings[r][0]
                
                if pt2 == False:
                    if battle(boss, player):
                        if min_cost == 0 or cost < min_cost:
                            min_cost = cost
                else:
                    if not battle(boss, player):
                        if max_cost == 0 or cost > max_cost:
                            max_cost = cost
    return min_cost if not pt2 else max_cost

def main():
    path = "puzzle_input.txt"

    weapons = { # Cost, Damage
        "dagger": [8, 4],
        "shortsword": [10, 5],
        "warhammer": [25, 6],
        "longsword": [40, 7],
        "greataxe": [74, 8]
    }
    armor = {   # Cost, Armor
        "leather": [13, 1],
        "chainmail": [31, 2],
        "splintmail": [53, 3],
        "bandedmail": [75, 4],
        "platemail": [102, 5],
        "nothing": [0, 0]
    }
    rings = {   # Cost, Damage, Armor
        "Damage +1": [25, 1, 0],
        "Damage +2": [50, 2, 0],
        "Damage +3": [100, 3, 0],
        "Defense +1": [20, 0, 1],
        "Defense +2": [40, 0, 2],
        "Defense +3": [80, 0, 3],
        "nothing": [0, 0, 0],
        "nothing2": [0, 0, 0]
    }

    cpu = [] # hitpoints, damage, armor

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            cpu.append(int(input[-1]))
    
    boss = Player(cpu[0], cpu[1], cpu[2])

    henry = Player(100, 0, 0)

    part1 = findMinMax(boss, henry, weapons, armor, rings, False)
    print(f"Part 1: {part1}")

    part2 = findMinMax(boss, henry, weapons, armor, rings, True)
    print(f"Part 2: {part2}")

if __name__ == "__main__":
    main()