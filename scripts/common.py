import spacy

from PatternMerger import PatternMerger


class WordSpellCorrectionPlaceholder(object):
    def __init__(self, spell_check):
        self.spell_check = spell_check

    def __call__(self, doc):
        for token in doc:
            token.set_extension("corrected", getter=lambda tok: tok.text.lower(), force=True)
            if self.spell_check:
                # spell correction logic goes here
                pass
        doc.set_extension("corrected", getter=lambda d: d.text, force=True)
        return doc


def get_nlp(model, pattern, spell):
    nlp = spacy.load(model)
    pattern_merger = PatternMerger(nlp)
    spell_correcter = WordSpellCorrectionPlaceholder(spell)
    nlp.add_pipe(spell_correcter, first=True)
    if pattern:
        nlp.add_pipe(pattern_merger, first=True)
    return nlp


def doc2json(doc: spacy.tokens.Doc, pattern):
    json_doc = {
        "text": doc.text,
        "corrected": doc.get_extension("corrected")[2](doc)
    }
    tokens = [
        {
            "text": token.text,
            "idx": token.idx,
            "lemma": token.lemma_,
            "lower": token.lower_,
            "pos": token.pos_,
            "isPunct": token.is_punct,
            "isPattern": token.get_extension("is_pattern")[2](token) if pattern else None,
            "corrected": token.get_extension("corrected")[2](token)
        }
        for token in doc
    ]
    if not pattern:
        for token in tokens:
            del token["isPattern"]
    return {
        "doc": json_doc,
        "tokens": tokens,
    }
