import json
import csv
import re
# 输出的 CSV 文件路径
output_csv_file_path = 'output.csv'

def jsonToCsv():
    # 读取JSON文件
    with open('data.json', 'r', encoding='utf-8') as json_file:
        data = json.load(json_file)

    # 检查JSON数据结构，找到所有可能的列名
    fieldnames = set()
    for record in data:
        fieldnames.update(record.keys())

    # 写入CSV文件
    with open('data.csv', 'w', newline='', encoding='utf-8') as csv_file:
        writer = csv.DictWriter(csv_file, fieldnames=sorted(fieldnames))
        writer.writeheader()
        writer.writerows(data)

def parseCsv():
    import csv

    # 读取CSV文件路径
    csv_file_path = 'data.csv'
    maxtagcount = 0
    # 打开CSV文件并创建一个CSV阅读器对象
    with open(csv_file_path, newline='') as csvfile:
        # 读取输入 CSV 文件中的数据
        data_to_write = []
        reader = csv.reader(csvfile)
        next(reader)  # 跳过表头行
        # 遍历CSV文件的每一行
        for row in reader:
            # 在这里你可以对每一行进行处理
            questionName = row[2]  # 这里只是简单地打印每一行的内容
            questionTags = row[1]
            # 将单引号替换为双引号
            json_data = questionTags.replace("'", '"')
            # 修复 JSON 数据中的引号问题
            tags = json_data.replace('\\', '').replace('"[', '[').replace(']"', ']').replace('True','\'True\'').replace('None','\'None\'')
            # 使用正则表达式查找 "translatedName" 后面的值
            matches = re.findall(r'"translatedName": "(.*?)"', tags)
            # 生成表头
            header = ['question_name']
            for i in range(10):
                header.append(f'tag{i + 1}')

            # 填充标签，不足10个的用 'None' 填充
            tags_data = matches + ['None'] * (10 - len(matches))

            # 合并数据并打印
            data = [questionName] + tags_data
            # 合并数据并添加到要写入的列表中
            data_to_write.append(data)
    # 打开输出的 CSV 文件并创建一个 CSV 写入器对象
    with open(output_csv_file_path, 'w', newline='') as output_csvfile:
        writer = csv.writer(output_csvfile)
        # 写入表头
        header = ['question_name']
        for i in range(10):
            header.append(f'tag{i + 1}')
        writer.writerow(header)

        # 写入数据
        writer.writerows(data_to_write)

    print("CSV 文件已创建并成功写入数据。")
parseCsv()





