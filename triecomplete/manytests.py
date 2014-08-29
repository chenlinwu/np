#!/usr/bin/python
import sys
import os

for x in range(1, 9):
        os.system("java -cp bin trieComplete.TestTrie2 "+str(x*100))
