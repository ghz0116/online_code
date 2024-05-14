import re

import requests
from bs4 import BeautifulSoup

def getQuestionTags():
    # 定义要爬取的页面 URL
    url = 'https://leetcode.cn/problemset/'

    # 发送 GET 请求获取页面内容
    response = requests.get(url)

    # 使用 BeautifulSoup 解析 HTML
    soup = BeautifulSoup(response.text, 'html.parser')

    # 找到所有包含问题的元素
    questions = soup.find_all('a', class_='inline-flex items-center')  # 请替换 'div' 和 'class_' 的值为实际页面中包含问题的元素和类名

    # 输出所有问题
    for question in questions:
        question_a = question.getText().strip()
        question_name = re.sub(r'\d+', '', question_a)
        question_tag_href = question.get('href').strip()
        print(question_name)
        print(question_tag_href)

def getTagQuestions(tag):
    url = 'https://leetcode.cn/tag/array/problemset/'
    # 发送 GET 请求获取页面内容
    response = requests.get(url)
    # 使用 BeautifulSoup 解析 HTML
    soup = BeautifulSoup(response.text, 'html.parser')
    print(response.text)
    # 找到所有包含问题的元素
    questions = soup.find_all('a')  # 请替换 'div' 和 'class_' 的值为实际页面中包含问题的元素和类名
    # 输出所有问题
    for question in questions:
        question_a = question.getText().strip()
        print(question_a)

getTagQuestions('a')
