

from flask import Flask, request, make_response, jsonify, render_template
from flask_cors import CORS
from flask_restx import Api, Resource  # Api 구현을 위한 Api 객체 import

from sklearn.linear_model import SGDClassifier#3000
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer

from bs4 import BeautifulSoup
import re
import konlpy
import pandas as pd
import nltk

app = Flask(__name__)  # Flask 객체 선언, 파라미터로 어플리케이션 패키지의 이름을 넣어줌.
api = Api(app)  # Flask 객체에 Api 객체 등록
CORS(app)

def clean_korean_documents(documents):

    #텍스트 정제 (HTML 태그 제거)
    for i, document in enumerate(documents):
        document = BeautifulSoup(document, 'html.parser').text
        documents[i] = document

    #텍스트 정제 (특수기호 제거)
    # for i, document in enumerate(documents):
    #     document = re.sub(r'[^ ㄱ-ㅣ가-힣]', '', document) #특수기호 제거, 정규 표현식
    #     documents[i] = document

    #텍스트 정제 (형태소 분석)
    for i, document in enumerate(documents):
        okt = konlpy.tag.Okt()
        clean_words = []
        for word in okt.pos(document, stem=True): #어간 추출
            if word[1] in ['Noun', 'Verb', 'Adjective', 'Alpha', 'Number']: #명사, 동사, 형용사
                clean_words.append(word[0])
        document = ' '.join(clean_words)
        documents[i] = document

    # #텍스트 정제 (불용어 제거)
    df = pd.read_csv('https://raw.githubusercontent.com/cranberryai/todak_todak_python/master/machine_learning_text/clean_korean_documents/korean_stopwords.txt', header=None)
    df[0] = df[0].apply(lambda x: x.strip())
    stopwords = df[0].to_numpy()
    # nltk.download('punkt')
    for i, document in enumerate(documents):
        clean_words = []
        for word in nltk.tokenize.word_tokenize(document):
            if word not in stopwords: #불용어 제거
                clean_words.append(word)
        documents[i] = ' '.join(clean_words)

    return documents

@app.route('/predict', methods=['GET'])
def predict():
    if request.method == 'GET':
        #article = request.headers['article']
        # article = request.args.get('article')
        # tokenizer = AutoTokenizer.from_pretrained (
        # "seongju/klue-tc-bert-base-multilingual-cased"
        # )
        #
        # model = AutoModelForSequenceClassification.from_pretrained (
        # "seongju/klue-tc-bert-base-multilingual-cased"
        # )
        # mapping = {0: 'IT과학', 1: '경제', 2: '사회',
        # 3: '생활문화', 4: '세계', 5: '스포츠', 6: '정치'}
        # inputs = tokenizer(
        # article,
        #  padding=True, truncation=True, max_length=128, return_tensors="pt"
        #  )
        # outputs = model(**inputs)
        # probs = outputs[0].softmax(1)
        # s = probs.detach().numpy()[0].argsort()
        # prob_list = probs.detach().numpy()[0]
        # prob_list = prob_list/sum(prob_list) *100
        # output1 = mapping[s[-1]]
        # output2 = mapping[s[-2]]
        # output3 = mapping[s[-3]]
        # return jsonify({'output1':output1,'output1probs':int(prob_list[s[-1]]),
        #                 'output2':output2,'output2probs':int(prob_list[s[-2]]),
        #                 'output3':output3,'output3probs':int(prob_list[s[-3]]),
        #                 })
        article = request.args.get('article')

        processed_article = clean_korean_documents([article])

        train_df = pd.read_csv(r'train_data_processed.csv')
        vect = TfidfVectorizer()
        X = vect.fit_transform(train_df['processed_title'])
        model5_2 = SGDClassifier(loss = 'modified_huber',penalty = 'l2')
        model5_2.fit(X,train_df['topic_idx'])

        x = vect.transform(processed_article)

        probs = model5_2.predict_proba(x)


        mapping = {0: 'IT과학', 1: '경제', 2: '사회',
        3: '생활문화', 4: '세계', 5: '스포츠', 6: '정치'}
        s = probs[0].argsort()
        prob_list = probs[0]
        prob_list = prob_list/sum(prob_list) *100
        output1 = mapping[s[-1]]
        output2 = mapping[s[-2]]
        output3 = mapping[s[-3]]
        return jsonify({'output1':output1,'output1probs':int(prob_list[s[-1]]),
                        'output2':output2,'output2probs':int(prob_list[s[-2]]),
                        'output3':output3,'output3probs':int(prob_list[s[-3]]),
                        })

if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0',port = '5000')
