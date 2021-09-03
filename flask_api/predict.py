from transformers import AutoTokenizer, AutoModelForSequenceClassification

from flask import Flask, request, make_response, jsonify, render_template
from flask_cors import CORS
from flask_restx import Api, Resource  # Api 구현을 위한 Api 객체 import

app = Flask(__name__)  # Flask 객체 선언, 파라미터로 어플리케이션 패키지의 이름을 넣어줌.
api = Api(app)  # Flask 객체에 Api 객체 등록
CORS(app)

@app.route('/predict', methods=['GET'])
def predict():
    if request.method == 'GET':
        #article = request.headers['article']
        article = request.args.get('article')
        tokenizer = AutoTokenizer.from_pretrained (
        "seongju/klue-tc-bert-base-multilingual-cased"
        )

        model = AutoModelForSequenceClassification.from_pretrained (
        "seongju/klue-tc-bert-base-multilingual-cased"
        )
        mapping = {0: 'IT과학', 1: '경제', 2: '사회',
        3: '생활문화', 4: '세계', 5: '스포츠', 6: '정치'}
        inputs = tokenizer(
        article,
         padding=True, truncation=True, max_length=128, return_tensors="pt"
         )
        outputs = model(**inputs)
        probs = outputs[0].softmax(1)
        s = probs.detach().numpy()[0].argsort()
        prob_list = probs.detach().numpy()[0]
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
