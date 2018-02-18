# Credit:Kevin Davenport Machine Learning and Statistics Blog http://kldavenport.com
with open('temp.txt', 'r') as file:
	Data = [line.split() for line in file]

# print(Data)
# node for decisionTree
class node:
	def __init__(self, column = -1, value=None, results=None, leftBrnch=None, rightBrnch = None):
		self.column = column
		self.value = value
		self.results = results
		self.leftBrnch = leftBrnch
		self.rightBrnch = rightBrnch

# Spliting the data into two sets, based on values
def setDivision(Data, c, v):
	splitFunction = lambda Data:Data[c]==v
	set1 = [record for record in Data if splitFunction(record)]
	set2 = [record for record in Data if not splitFunction(record)]
	return (set1,set2)

# computing the cound of number of distinct actions
def getCounts(data):
	results = {}
	for record in data:
		r = record[len(record)-1]
		if r not in results: results[r] = 0
		results[r] += 1
	return results

# Calcuating entropy of the data
def getEntropy(Data):
	from math import log
	results = getCounts(Data)
	entorpy=0.0
	for record in results.keys():
		pi = float(results[record]/len(Data))
		entorpy = entorpy - pi*log(pi)
	return entorpy

#  Decision tree algorithm
def decisionTree(Data, entropy=getEntropy ):
	if len(Data) == 0:
		 return node()
	currentEntropy = entropy(Data)

	gainInformation = 0.0
	criteria = None
	optimalSet = None
	
	numOfColumns = len(Data[0]) - 1 
	for col in range(0 , numOfColumns):
		columnVals = set([row[col] for row in Data])
		for value in columnVals:
			set1, set2 = setDivision(Data, col, value)
			pi = float(len(set1)/ len(Data))
			gain = currentEntropy - pi*entropy(set1) - (1-pi)*entropy(set2)
			if gain > gainInformation and len(set1) > 0 and len(set2)>0:
				gainInformation = gain
				criteria = (col,value)
				optimalSet = (set1, set2)

	if gainInformation > 0:
		leftBranch = decisionTree(optimalSet[0])
		rightBranch = decisionTree(optimalSet[1])
		return node(column=criteria[0], value=criteria[1], leftBrnch=leftBranch, rightBrnch=rightBranch)
	else :
		return node(results=getCounts(Data))

def printDT(tree, indent=''):
	if tree.results !=None:
		print(str(tree.results))
	else:
		print('Column' + str(tree.column) + ' : ' + str(tree.value)+'? ')
		print(indent+'Left->',)
		printDT(tree.leftBrnch, indent+'  ')
		print (indent+'Right->',)
		printDT(tree.rightBrnch, indent+'  ')
		
printDT(decisionTree(Data))