#this is a findDuplicates file with comments
def find_duplicate_bitvec(A):
    d = 0
    for index, item in enumerate(A): #some inline comment
        bitmask = 1 << item
        if d & bitmask:
            return item
        else:
            d |= bitmask