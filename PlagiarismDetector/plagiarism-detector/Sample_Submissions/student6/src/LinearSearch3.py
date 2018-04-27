#function to implement linear search
#pass an array and the item to find
def lSearch(element,array):
    present = False
    index = 0
    while index < len(array) and not present:
        if array[position] == element:
            present = True
        index = index + 1
    return present