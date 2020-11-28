# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import sys
import spacy
import json

from spacy.matcher import Matcher
from spacy.tokens import DocBin

from common import get_nlp, doc2json

if __name__ == '__main__':
    model = sys.argv[1]
    pattern = sys.argv[2] == "true"
    spell = sys.argv[3] == "true"
    docs = []
    text = input()
    while text:
        docs.append(text)
        text = input()
    processed_docs = []
    nlp = get_nlp(model, pattern, spell)
    for doc in docs:
        print(doc2json(nlp(doc), pattern))
