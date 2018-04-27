def bS(a, left, right, x):
 
    while left <= right:
 
        mid = left + (right - l)/2;
         
        # Check if x is present at mid
        if a[mid] == x:
            return mid
 
        # If x is greater, ignore left half
        elif a[mid] < x:
            left = mid + 1
 
        # If x is smaller, ignore right half
        else:
            right = mid - 1
     
    # If we reach here, then the element
    # was not present
    return -1
 
 
# Test array
a = [ 2, 3, 4, 10, 40 ]
x = 10
 
# Function call
result = binarySearch(a, 0, len(a)-1, x)
 
if result != -1:
    print "Element is present at index %d" % result
else:
    print "Element is not present in array"