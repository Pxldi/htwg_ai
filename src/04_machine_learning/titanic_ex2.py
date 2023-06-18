import pandas as pd
from sklearn.metrics import accuracy_score
from sklearn.linear_model import LinearRegression
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
import os

# a)


# Set the working directory
dirname = os.path.dirname(__file__)

# Read the data (only Pclass)
train_val = pd.read_csv(os.path.join(dirname, 'titanic_src/train.csv'), usecols=['Pclass', 'Survived'])

train_val.head()

# Split the data into train and test data
train = train_val[:710]
test = train_val[710:]

# Create the model and train it
model = LinearRegression()
model.fit(train[['Pclass']], train['Survived'])

# Predict the test data
pred = model.predict(test[['Pclass']])

# Round the prediction to 0 or 1
pred = pred.round()

# Calculate the accuracy
accuracy = accuracy_score(test['Survived'], pred)
print('Accuracy: ', accuracy)

# b)

# Read the data (only Pclass)
train_val = pd.read_csv(os.path.join(dirname, 'titanic_src/train.csv'), usecols=['Pclass', 'Survived'])
test_val = pd.read_csv(os.path.join(dirname, 'titanic_src/test.csv'))

# Create the model and train it
model = LinearRegression()
model.fit(train_val[['Pclass']], train_val['Survived'])

# Predict the test data
pred = model.predict(test_val[['Pclass']])
pred = pred.round().astype(int)

output = pd.DataFrame({'PassengerId': test_val.PassengerId, 'Survived': pred})
output.to_csv('linear_regression.csv', index=False)
print("Your submission was successfully saved!")

# c)

# Read the data (only Pclass)
train_val = pd.read_csv(os.path.join(dirname, 'titanic_src/train.csv'), usecols=['Pclass', 'Survived'])
test_val = pd.read_csv(os.path.join(dirname, 'titanic_src/test.csv'))

# Create the model and train it
model = LogisticRegression()
model.fit(train_val[['Pclass']], train_val['Survived'])

# Predict the test data
pred = model.predict(test_val[['Pclass']])
pred = pred.round().astype(int)

output = pd.DataFrame({'PassengerId': test_val.PassengerId, 'Survived': pred})
output.to_csv('logistic_regression.csv', index=False)
print("Your submission was successfully saved!")

# d)

# Read the data
train_val = pd.read_csv(os.path.join(dirname, 'titanic_src/train.csv'))
test_val = pd.read_csv(os.path.join(dirname, 'titanic_src/test.csv'))

# Insert missing age values
train_val['Age'] = train_val['Age'].fillna(train_val['Age'].median(skipna=True), inplace=True)
test_val['Age'] = test_val['Age'].fillna(test_val['Age'].median(skipna=True), inplace=True)

# Convert variables Pclass and Sex to numerical values
train_val = pd.get_dummies(train_val, columns=['Sex'])
test_val = pd.get_dummies(test_val, columns=['Sex'])

train_val = pd.get_dummies(train_val, columns=['Pclass'])
test_val = pd.get_dummies(test_val, columns=['Pclass'])

print(train_val.head())

# Create the model and train it
model = LogisticRegression()
features = ['Pclass_1', 'Pclass_2', 'Pclass_3', 'Sex_female', 'Sex_male']
model.fit(train_val[features], train_val['Survived'])

# Predict the test data
pred = model.predict(test_val[features])
pred = pred.round().astype(int)

output = pd.DataFrame({'PassengerId': test_val.PassengerId, 'Survived': pred})
output.to_csv('logistic_regression2.csv', index=False)
print("Your submission was successfully saved!")

# e)
train_val = pd.read_csv(os.path.join(dirname, 'titanic_src/train.csv'))
test_val = pd.read_csv(os.path.join(dirname, 'titanic_src/test.csv'))
train_val['Age'] = train_val['Age'].fillna(train_val['Age'].median(skipna=True))
test_val['Age'] = test_val['Age'].fillna(test_val['Age'].median(skipna=True))
train_val = pd.get_dummies(train_val, columns=['Sex'])
test_val = pd.get_dummies(test_val, columns=['Sex'])
train_val = pd.get_dummies(train_val, columns=['Pclass'])
test_val = pd.get_dummies(test_val, columns=['Pclass'])

# Create the model and train it
rf = RandomForestClassifier(n_estimators=100, max_depth=5, random_state=1)
features = ['Pclass_1', 'Pclass_2', 'Pclass_3', 'Sex_female', 'Sex_male', 'Age']
rf.fit(train_val[features], train_val['Survived'])

# Predict the test data
pred = rf.predict(test_val[features])
pred = pred.round().astype(int)

output = pd.DataFrame({'PassengerId': test_val.PassengerId, 'Survived': pred})
output.to_csv('random_forest.csv', index=False)
print("Your submission was successfully saved!")
