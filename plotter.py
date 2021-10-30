__author__ = "Dennis Heitkamp"

import matplotlib.pyplot as plt
import numpy as np

x = [[0,0], [5.5, 7],[10,10], [15,7],[20, 5], [30 ,5], [35, 10],[40, 15], [50, 20], [52, 15],[55 ,0 ]]
X = np.array(x)
y = X[:,1]
x = X[:,0]

mymodel = np.poly1d(np.polyfit(x, y, 6))
myline = np.linspace(1, 55, 100)
myline_2 = np.linspace(15, 50, 100)
myline_3 = np.linspace(33, 55, 100)

plt.xlabel("Fortschritt")
plt.ylabel("Spannung")
plt.plot(myline, mymodel(myline))
plt.plot(myline_2, mymodel(myline_2), color='g')
plt.plot(myline_3, mymodel(myline_3), color='r')
plt.axis('on')
plt.xticks([])
plt.yticks([])
plt.text(3,2, 'Start')
plt.text(5,10, 'Laserturm')
plt.text(20,6, 'Erkunden')
plt.text(45,20, 'Boss')
plt.text(49,10, 'Sieg')
#plt.show()
plt.savefig('./test.png')