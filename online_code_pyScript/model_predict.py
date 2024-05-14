import argparse
import json

import joblib

if __name__ == '__main__':
    ##从命令行中读取参数
    parser = argparse.ArgumentParser()
    parser.add_argument('questionName', type=str, help='The question to predict tags for.')
    args = parser.parse_args()
    print(args.questionName)
    # 加载训练好的模型和TF-IDF向量化器
    loaded_pipeline = joblib.load('simple_pipeline_model.joblib')
    # 加载测试数据
    test_questions = [args.questionName]
    # 使用加载的模型进行预测
    predicted_tags = loaded_pipeline.predict(test_questions)
    list = predicted_tags[0].tolist()
    tags=''
    for tag in list:
        tags+=tag+'-'
    print(json.dumps(tags[:-1]))