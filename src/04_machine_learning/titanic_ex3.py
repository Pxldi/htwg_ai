import pandas as pd
import os
import tensorflow as tf
import matplotlib.pyplot as plt
import tensorflow.keras
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense

dirname = os.path.dirname(__file__)

# Read the data
train_val = pd.read_csv(os.path.join(dirname, 'titanic_src/train.csv'),
                        usecols=['PassengerId', 'Survived', 'Pclass', 'Sex', 'Age'])
test_val = pd.read_csv(os.path.join(dirname, 'titanic_src/test.csv'),
                       usecols=['PassengerId', 'Pclass', 'Sex', 'Age'])
train_val['Age'] = train_val['Age'].fillna(train_val['Age'].median(skipna=True))
test_val['Age'] = test_val['Age'].fillna(test_val['Age'].median(skipna=True))
train_val = pd.get_dummies(train_val, columns=['Sex'])
test_val = pd.get_dummies(test_val, columns=['Sex'])
features = ['Pclass', 'Age', 'Sex_female', 'Sex_male']

model = Sequential()
model.add(Dense(100, activation='sigmoid', batch_input_shape=(None, 4)))
model.add(Dense(50, activation='sigmoid'))
model.add(Dense(10, activation='sigmoid'))
model.add(Dense(1, activation='sigmoid'))
opt = tf.keras.optimizers.Adam(learning_rate=1e-3)

model.compile(loss=tf.keras.losses.BinaryCrossentropy(),
              optimizer=opt,
              metrics=['accuracy'])

history = model.fit(train_val[features].astype('float32'), train_val['Survived'].astype('float32'),
                    epochs=100, batch_size=32, verbose=0)

plt.plot(history.history['accuracy'])
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.title('Model accuracy')
plt.show()
plt.plot(history.history['loss'])
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.title('Model loss')
plt.show()

# Predict the test data
pred = model.predict(test_val[features].astype('float32'))
pred = pred.round().astype(int)

pred = pred.flatten()

output = pd.DataFrame({'PassengerId': test_val.PassengerId, 'Survived': pred})
output.to_csv('neural_network.csv', index=False)
print("Your submission was successfully saved!")

model.summary()
