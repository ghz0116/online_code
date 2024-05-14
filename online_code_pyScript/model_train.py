import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.ensemble import RandomForestClassifier
from sklearn.pipeline import Pipeline
import joblib

# 读取简化后的CSV文件，假设名为 'output.csv'
data = pd.read_csv('output.csv', encoding='gbk').fillna('None')

# 分割特征和标签
X = data['question_name']
y = data[['tag1', 'tag2', 'tag3']]

# 创建管道
pipeline = Pipeline([
    ('tfidf', TfidfVectorizer()),
    ('clf', RandomForestClassifier(n_estimators=100, random_state=42))
])

# 训练管道
pipeline.fit(X, y)

# 保存模型到本地
joblib.dump(pipeline, 'simple_pipeline_model.joblib')
