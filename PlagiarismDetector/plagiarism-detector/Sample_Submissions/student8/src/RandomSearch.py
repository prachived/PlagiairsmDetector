#function to implement linear search
#pass an array and the item to find
def RandomSearch(element,array):
    index = random.randint(0, len(array) - 1)
    if array[position] == element:
        return True
    else:
        return RandomSearch(element, array)