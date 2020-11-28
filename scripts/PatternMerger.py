from spacy.matcher import Matcher


class PatternMerger(object):
    def __init__(self, nlp):
        self.matcher = Matcher(nlp.vocab)
        pattern = [{"TEXT": {"REGEX": "\$"}}, {"TEXT": {"REGEX": "\w+"}}]
        self.matcher.add('NounChunks', None, pattern)

    def __call__(self, doc):
        # this function is called on the Doc object in the pipeline
        matches = self.matcher(doc)
        for token in doc:
            token.set_extension("is_pattern", getter=lambda tok: tok.text.startswith("$"), force=True)
        spans = [doc[start:end] for match_id, start, end in matches]
        for span in spans:
            token = span.merge()
            token.set_extension("is_pattern", getter=lambda tok: tok.text.startswith("$"), force=True)
        return doc
