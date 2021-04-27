from itertools import combinations_with_replacement, permutations

spells = {  # Mana Cost, Damage, Heals
        "Magic Missle": [53, 4, 0],
        "Drain": [73, 2, 2],
        "Shield": [113, 0, 0], # +7 armor for 6 turns
        "Poison": [173, 0, 0], # enemy -3 HP each turn for 6 turns, set to zero because it doesn't immediately inflict damage
        "Recharge": [229, 0, 0] # +101 mana each turn for 5 turns
    }

class Player:
    def __init__(self, hp: int, damage: int, wiz: bool):
        self.hp = hp
        self.dmg = damage
        self.rmr = 0
        self.mana = 0 if not wiz else 500
        self.spentMANA = 0
        self.defaultHP = hp
        self.defaultDMG = damage
        self.defaultRMR = 0
        self.defaultMANA = 0 if not wiz else 500
        self.defaultSpentMANA = 0
    
    def getHP(self) -> int:
        return self.hp
    
    def getDMG(self) -> int:
        return self.dmg
    
    def getRMR(self) -> int:
        return self.rmr
    
    def getMANA(self) -> int:
        return self.mana

    def getSpentMANA(self) -> int:
        return self.spentMANA

    def useMANA(self, loss: int):
        self.mana -= loss
        self.spentMANA += loss

    def gainMANA(self, gain: int):
        self.mana += gain
    
    def loseHP(self, loss: int):
        self.hp -= loss

    def gainHP(self, gain: int):
        self.hp += gain

    def setShield(self):
        self.rmr = 7

    def rmvShield(self):
        self.rmr = 0

    def reset(self):
        self.hp = self.defaultHP
        self.dmg = self.defaultDMG
        self.rmr = self.defaultRMR
        self.mana = self.defaultMANA
        self.spentMANA = self.defaultSpentMANA

def battle(boss, player, avail_spells: list[str]): #, spells: dict) -> bool:
    global spells
    boss.reset()
    player.reset()

    shield = False
    shield_count = 0
    poison = False
    poison_count = 0
    recharge = False
    recharge_count = 0
    turn = True
    spell_tracker = 0
    got_mana = True

    while True:
        if shield == True:
            shield_count -= 1
            if shield_count == 0:
                player.rmvShield()
                shield = False
        if poison == True:
            boss.loseHP(3)
            poison_count -= 1
            if poison_count == 0:
                poison = False
        if recharge == True:
            player.gainMANA(101)
            recharge_count -= 1
            if recharge_count == 0:
                recharge = False
    
        if turn == True:    # Player's move
            if player.getMANA() >= 53:
                for _ in range(len(avail_spells)):
                    if spell_tracker < len(avail_spells):
                        spell = avail_spells[spell_tracker]
                        if player.getMANA() >= spells[spell][0]:
                            player.useMANA(spells[spell][0])
                            boss.loseHP(spells[spell][1])
                            player.gainHP(spells[spell][2])
                            spell_tracker += 1
                            break
                        else:
                            spell_tracker += 1
                    else:
                        spell_tracker = 0
            else:
                got_mana = False

            if spell == "Shield":
                shield = True
                shield_count = 6
            elif spell == "Poison":
                poison = True
                poison_count = 6
            elif spell == "Recharge":
                recharge = True
                recharge_count = 5
            turn = False
        
        else:   # Boss' turn
            player.loseHP(max(boss.getDMG() - player.getRMR(), 1))
            turn = True
        
        if boss.getHP() <= 0:
            return (True, player.getSpentMANA())
        elif player.getHP() <= 0:
            return (False, 0)
        elif got_mana == False:
            return (False, 0)

def findMinMana(boss, player):#, spells: dict):
    global spells
    min_mana = 0
    # for i in range(1, 10):
    # for i in tqdm(range(1,10), desc="Solving...",ascii=False,ncols=75):
    for x in combinations_with_replacement(spells.keys(), 10):
        for y in permutations(list(x), len(list(x))):
            result = battle(boss, player, list(y))
            if result[0] == True:
                if min_mana == 0 or result[1] < min_mana:
                    min_mana = result[1]
    return min_mana

def main():
    path = "puzzle_input.txt"

    cpu = [] # hitpoints (hp) and damage

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            cpu.append(int(input[-1]))

    boss = Player(cpu[0], cpu[1], False)
    henry = Player(50, 0, True)

    print(findMinMana(boss, henry))

    test_b = Player(13, 8, False)
    test_p = Player(10, 0, True)
    # print(findMinMana(test_b, test_p))

if __name__ == "__main__":
    main()