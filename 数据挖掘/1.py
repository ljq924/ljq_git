import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
import numpy as np

X = np.array([2, 4, 6, 8, 10]).reshape(-1, 1)
y = np.array([65, 70, 78, 85, 92])

plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False

plt.scatter(X, y, color='blue', label='学生数据')
plt.xlabel('每周学习时长（小时）')
plt.ylabel('Python成绩（分）')
plt.title('学习时长与Python成绩的关系')
plt.grid(True, linestyle='--', alpha=0.6)

model = LinearRegression()
model.fit(X, y)

y_pred = model.predict(X)
plt.plot(X, y_pred, color='red', linewidth=2, label='回归线')
plt.legend()
plt.show()

a = model.coef_[0]
b = model.intercept_
print(f"回归方程：Python成绩 = {a:.2f} × 每周学习时长 + {b:.2f}")