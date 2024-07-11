import random
from flask import Flask, request, render_template, jsonify
import spacy
import json
import re

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('chatbot.html')

# Cargar modelo de spaCy en español
nlp = spacy.load('es_core_news_sm')

# Cargar los datos de intents desde el archivo JSON
with open('intents.json', 'r', encoding='utf-8') as f:
    intents = json.load(f)

def clean_text(text):
    text = text.lower()
    text = re.sub(r'[^a-záéíóúñü\s]', '', text)
    return text


'''@app.route('/chatbot', methods=['POST'])
def chatbot():
    data = request.args.get('msg')
    if not data or 'message' not in data:
        return jsonify({'response': "Error: No se recibió ningún mensaje"}), 400
    
    message = data['message']
    response = chatbot_response(message)
    return chatbot_response(data)'''

@app.route('/chatbot', methods=['POST'])
def chatbot():
    data = request.get_json()
    if not data or 'message' not in data:
        return jsonify({'response': "Error: No se recibió ningún mensaje"}), 400
    
    message = data['message']
    response = chatbot_response(message)
    #return response
    return jsonify({'response': response})

def chatbot_response(msg):
    msg = clean_text(msg)
    doc = nlp(msg)
    lemmatized_msg = " ".join([token.lemma_ for token in doc])
    
    
    
    '''for ent in doc.ents:
        if ent.label_ == "LOC":  # Detectar si hay una entidad de tipo ubicación
            return f"Procesando ubicación: {ent.text}"'''
    
    # Si no se encuentra una ubicación, buscar en los intents
    for intent in intents['intents']:
        for pattern in intent['patterns']:
            lemmatized_pattern = " ".join([token.lemma_ for token in nlp(pattern)])
            if lemmatized_pattern in lemmatized_msg:
                return random.choice(intent['responses'])

    return "No entendí tu mensaje. ¿Puedes especificar más detalles?"

if __name__ == '__main__':
    app.run(debug=True)
