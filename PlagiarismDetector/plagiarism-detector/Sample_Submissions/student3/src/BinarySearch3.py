def bS(a, left, right, elem):
 
    while left <= right:
 
        mid = left + (right - l)/2;
         
        # Check if x is present at mid
        if a[mid] == elem:
            return mid
 
        # If x is greater, ignore left half
        elif a[mid] < elem:
            left = mid + 1
 
        # If x is smaller, ignore right half
        else:
            right = mid - 1
     
    # If we reach here, then the element
    # was not present
    return -1
 
 
# Test array
search_a = [ 30, 31, 87, 94, 98 ]
x = 98
 
# Function call
result = binarySearch(search_a, 0, len(search_a)-1, x)
 
if result != -1:
    print "Element is present at index %d" % result
else:
    print "Element is not present in array"