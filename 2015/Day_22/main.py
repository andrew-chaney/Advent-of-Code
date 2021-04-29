spells = {  # Mana Cost, Damage, Heals
    "Magic Missle": [53, 4, 0],
    "Drain": [73, 2, 2],
    "Shield": [113, 0, 0],  # +7 armor for 6 turns
    # enemy -3 HP each turn for 6 turns, set to zero because it doesn't immediately inflict damage
    "Poison": [173, 0, 0],
    "Recharge": [229, 0, 0]  # +101 mana each turn for 5 turns
}

def rndBeginning(bHP, pRMR, pMANA, shield, poison, recharge):
    if shield > 0:
        pRMR = 7
        shield -= 1
    if poison > 0:
        bHP -= 3
        poison -= 1
    if recharge > 0:
        pMANA += 101
        recharge -= 1
    return (bHP, pRMR, pMANA, shield, poison, recharge)

def battle(bHP, bDMG, pHP, pMANA, minSpent, spent, start_spell, shield, poison, recharge, part2):
    global spells
    pRMR = 0

    # Actions that happen at the beginning of each round
    if minSpent <= spent:
        return minSpent
    
    if part2:
        pHP -= 1
        if pHP <= 0:
            return minSpent

    bHP, pRMR, pMANA, shield, poison, recharge = rndBeginning(bHP, pRMR, pMANA, shield, poison, recharge)
    # See if the boss is dead yet
    if bHP <= 0:
        return min(minSpent, spent)
    
    # Player's turn
    spent += spells[start_spell][0]
    pMANA -= spells[start_spell][0]
    bHP -= spells[start_spell][1]
    pHP += spells[start_spell][2]
    if start_spell == "Shield":
        shield = 6
    elif start_spell == "Poison":
        poison = 6
    elif start_spell == "Recharge":
        recharge = 5
    if bHP <= 0:
        return min(minSpent, spent)

    # Actions that happen at the beginning of each round
    if minSpent <= spent:
        return minSpent

    if part2:
        pHP -= 1
        if pHP <= 0:
            return minSpent

    bHP, pRMR, pMANA, shield, poison, recharge = rndBeginning(bHP, pRMR, pMANA, shield, poison, recharge)
    # See if the boss is dead yet
    if bHP <= 0:
        return min(minSpent, spent)

    # Boss' turn
    pHP -= max(bDMG - pRMR, 1)
    if pHP <= 0:
        return minSpent

    # If nobody is dead yet, keep playing
    for spell in spells.keys():
        if spells[spell][0] <= pMANA:
            minSpent = min(minSpent, battle(bHP, bDMG, pHP, pMANA, minSpent, spent, spell, shield, poison, recharge, part2))

    return minSpent

def findMinMana(bHP, bDMG, pHP, pMANA, part2):
    global spells

    min_mana = 1000000
    for spell in spells.keys():
        results = battle(bHP, bDMG, pHP, pMANA, min_mana, 0, spell, 0, 0, 0, part2)
        min_mana = min(min_mana, results)
    return min_mana


def main():
    path = "puzzle_input.txt"

    cpu = []  # hitpoints (hp) and damage

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            cpu.append(int(input[-1]))

    bossHP = cpu[0]
    bossDMG = cpu[1]
    henryHP = 50
    henryMANA = 500

    part1 = findMinMana(bossHP, bossDMG, henryHP, henryMANA, part2=False)
    part2 = findMinMana(bossHP, bossDMG, henryHP, henryMANA, part2=True)

    print(f"Part 1: {part1}")
    print(f"Part 2: {part2}")

if __name__ == "__main__":
    main()
