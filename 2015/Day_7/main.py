import re

# GLOBAL
completed_signals = {}

# Solve for outgoing signal using recursion
def outgoingSig(signals: dict, wire: str) -> int:
    global completed_signals

    if wire not in completed_signals:
        # If we don't know the outgoing signal of the wire, find it
        input = signals[wire]
        # If the outgoing signal for the wire is explicit, return the signal
        if len(input) == 1:
            # If the outgoing signal is a digit, specify the int
            if input[0].isdigit():
                completed_signals[wire] = int(input[0])
            # If the outgoing signal is a reference to the signal of another wire, find the outgoing signal of that wire
            else:
                completed_signals[wire] = outgoingSig(signals, input[0])
        # Handle NOT actions
        elif len(input) == 2:
            if input[1].isdigit():
                completed_signals[wire] = ~int(input[1]) & 0xffff
            else:
                completed_signals[wire] = ~outgoingSig(signals, input[1]) & 0xffff
        # Handle other actions
        else:
            # Find the action
            action = input[1]
            # Find the incoming signals
            a = int(input[0]) if input[0].isdigit() else outgoingSig(signals, input[0])
            b = int(input[2]) if input[2].isdigit() else outgoingSig(signals, input[2])
            # Apply the operator and return the current wire's outgoing signal
            if action == "AND":
                completed_signals[wire] = a & b
            elif action == "OR":
                completed_signals[wire] = a | b
            elif action == "LSHIFT":
                completed_signals[wire] = a << b
            elif action == "RSHIFT":
                completed_signals[wire] = a >> b
    
    return completed_signals[wire]

def main():
    path = "puzzle_input.txt"
    file = open(path, "r")

    # Dictionary to store wire and their associated signals
    signals = {}

    for line in file:
        # Parse line contents
        contents = line.split(" -> ")
        contents = [i.strip() for i in contents]
        # Identify wire being assigned a signal
        wire = contents[1]
        # Identify incoming signal or signals and action
        input = re.findall(r'\w+', contents[0])
        signals[wire] = input

    file.close()

    # Part 1
    desired_wire = 'a'
    answer = outgoingSig(signals, desired_wire)
    print(f"Outgoing Signal of Wire '{desired_wire}': {answer}")

    # Part 2
    # Reset completed signals to be able to reconfigure the entire circuit
    global completed_signals
    completed_signals = {}
    override_wire = 'b'
    completed_signals[override_wire] = answer
    answer2 = outgoingSig(signals, desired_wire)
    print(f"NEW Outgoing Signal of Wire '{desired_wire}': {answer2}")

if __name__=="__main__":
    main()
