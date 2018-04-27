def bSort(a):
	bSortHelper(a)

def bSortHelper(a)
for p in r(len(a)-1,0,-1):
        for i in r(p):
            if a[i]>a[i+1]:
                temp = a[i]
                a[i] = a[i+1]
                a[i+1] = temp
                
a = [20,30,93,17,31,35,44,55,29]
bSort(a)
print(a)