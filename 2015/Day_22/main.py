from random import randint
from tqdm import tqdm

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

def castSpell() -> str:
    num = randint(0, 4)
    if num == 0:
        return "Magic Missle"
    elif num == 1:
        return "Drain"
    elif num == 2:
        return "Shield"
    elif num == 3:
        return "Poison"
    else:
        return "Recharge"

def battle(boss, player):#, avail_spells: list[str]): #, spells: dict) -> bool:
    global spells
    boss.reset()
    player.reset()

    shield = 0
    poison = 0
    recharge = 0

    turn = 0
    while True:
        if shield > 0:
            shield -= 1
            if shield == 0:
                player.rmvShield()
        if poison > 0:
            boss.loseHP(3)
            poison -= 1
        if recharge > 0:
            player.gainMANA(101)
            recharge -= 1
    
        if turn % 2 == 0:    # Player's move
            if player.getMANA() >= 53:
                while True:
                    spell = castSpell()
                    if spells[spell][0] <= player.getMANA():
                        player.useMANA(spells[spell][0])
                        boss.loseHP(spells[spell][1])
                        player.gainHP(spells[spell][2])

                        if spell == "Shield":
                            shield = 6
                        elif spell == "Poison":
                            poison = 6
                        elif spell == "Recharge":
                            recharge = 5
                        break
        
        else:   # Boss' turn
            player.loseHP(max(boss.getDMG() - player.getRMR(), 1))
        
        if boss.getHP() <= 0:
            return (True, player.getSpentMANA())
        elif player.getHP() <= 0:
            return (False, 0)
        else:
            turn += 1

def findMinMana(boss, player):#, spells: dict):
    global spells
    min_mana = 1000000
    for i in tqdm(range(1_000_000), desc="Solving...", ascii=False, ncols=100):
        results = battle(boss, player)
        if results[0] == True:
            # print("FINALLY, A WIN GOOD SIR.")
            if results[1] < min_mana:
                min_mana = results[1]
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

    # print(findMinMana(boss, henry))

    test_b = Player(13, 8, False)
    test_p = Player(10, 0, True)
    print(findMinMana(test_b, test_p))

if __name__ == "__main__":
    main()