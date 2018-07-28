
# coding: utf-8

# In[8]:

import nltk, random, logging, math, graph
from nltk.corpus import gutenberg, brown, stopwords, reuters, PlaintextCorpusReader
from math import log
   
# compute similarity between two sentences depending on common tokens
def similarity(s1,s2):
    count = len([w for w in s1 if w in s2]) # number of words in both sentences
    if (log(len(s1)) + log(len(s2))) == 0:
        return 0
    else:
        return (count / (log(len(s1)) + log(len(s2)))) # similarity value (see TextRank paper)

def compute_textrank(graph_out, iterations):
    d = 0.85 # dump value prevents 0 values
    size = len(graph_out)  
    WS_V = [] 
    # initialize algorithm with some values (can be random)
    for i in range(0,size):
        WS_V.append([1, i]) 
    
    # for details on the modified PageRank algorithm see TextRank paper
    for sth in range(0,iterations):
        i=0
        for v in graph_out:
            outer_sum=0
            i+=1
            if len(v) > 0:
                i = v[0][0]
                for v_in in v:
                    inner_sum=0
                    j=v_in[1]
                    outgoings=graph_out[j]
                    if len(outgoings) > 0:
                        for v_out in outgoings:
                            inner_sum+=v_out[2]
                    if inner_sum != 0:
                        outer_sum+=(v_in[2]/inner_sum)*WS_V[j][0]
                WS_V[i][0] = (1-d)+d*outer_sum
            else:
                WS_V[i][0] = 0 # there is no similarity between sentences

    return WS_V
             
# help method to convert a word array to a string
def sentToString(s):
    string = ''
    for w in s:
        if w.isalnum():
            string+=' '
        string+=w
    return string
    
print('Welcome to the extractive single-document summarizer')
iterations=20 # iterations for textrank - should be 10 or more to converge
n = 2 # output n sentences as summary
corpus_root = './articles/' # news articles to be summarized
wordlists = PlaintextCorpusReader(corpus_root, '.*')
print_pagerank_values = False

for fileid in wordlists.fileids():
    print('Article: ', fileid)
    sents = wordlists.sents(fileid)
    sim_graph_out = []
    size = len(sents)
    s1_count=0
    print('Computing sentence similarity graph ...')
    # compute similarity of every sentence to all other sentences
    for i in range(0,size):
        s1=sents[i]
        edges_out = []
        for j in range(0,size):
            s2=sents[j]
            sim = similarity(s1,s2)
            if sim > 0 and i != j:
                edges_out.append((i,j,sim))
        sim_graph_out.append(edges_out)

    print('Computing TextRank on graph ...')
    tr=compute_textrank(sim_graph_out, iterations)
    tr=sorted(tr, reverse=True)
    if print_pagerank_values:
        print('~~ PageRank values for selected sentences ~~')
        print(tr[:n])
    print('~~ Baseline Summary (first ', n, ' sentences) ~~')
    for sent in sents[:n]:
        print(sentToString(sent))
    print('~~ System Summary ~~')
    top_sents = [sents[j] for i,j in tr[:n]]
    for sent in top_sents:
        print(sentToString(sent))


# In[ ]:




# In[ ]:



