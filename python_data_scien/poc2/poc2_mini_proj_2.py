"""
Student code for Word Wrangler game
"""

import urllib2
import codeskulptor
import poc_wrangler_provided as provided

WORDFILE = "assets_scrabble_words3.txt"


# Functions to manipulate ordered word lists

def remove_duplicates(list1):
    """
    Eliminate duplicates in a sorted list.

    Returns a new sorted list with the same elements in list1, but
    with no duplicates.

    This function can be iterative.
    """
    result_list = []
    for idx in range(len(list1)):
        if idx == 0:
            result_list.append(list1[idx])
        else:
            if list1[idx] == result_list[-1]:
                pass
            else:
                result_list.append(list1[idx])
    return result_list

def intersect(list1, list2):
    """
    Compute the intersection of two sorted lists.

    Returns a new sorted list containing only elements that are in
    both list1 and list2.

    This function can be iterative.
    """
    result_list = []
    idx1 = 0
    idx2 = 0
    while idx1 < len(list1) and idx2 < len(list2):
        if list1[idx1] == list2[idx2]:
            result_list.append(list1[idx1])
            idx1 += 1
            idx2 += 1
        elif list1[idx1] < list2[idx2]:
            idx1 += 1
        elif list1[idx1] > list2[idx2]:
            idx2 += 1
        else:
            print 'error in func intersect!!!'
            return
    return result_list

# Functions to perform merge sort

def merge(list1, list2):
    """
    Merge two sorted lists.

    Returns a new sorted list containing those elements that are in
    either list1 or list2.

    This function can be iterative.
    """   
    result_list = []
    idx1 = 0
    idx2 = 0
    if len(list1) == 0 and len(list2) == 0:
        return []
    elif len(list1) != 0 and len(list2) == 0:
        return list1
    elif len(list1) == 0 and len(list2) != 0:
        return list2

    while True:
        if list1[idx1] == list2[idx2]:
            result_list.append(list1[idx1])
            result_list.append(list2[idx2])
            idx1 += 1
            idx2 += 1
        elif list1[idx1] < list2[idx2]:
            result_list.append(list1[idx1])
            idx1 += 1
        elif list1[idx1] > list2[idx2]:
            result_list.append(list2[idx2])
            idx2 += 1
        else:
            print 'error in func merge!!! p1'
            return

        if idx1 == len(list1) and idx2 < len(list2):
            result_list.extend(list2[idx2:])
            break
        elif idx1 < len(list1) and idx2 == len(list2):
            result_list.extend(list1[idx1:])
            break
        elif idx1 == len(list1) and idx2 == len(list2):
            break
        else:
            pass
    return result_list
                
def merge_sort(list1):
    """
    Sort the elements of list1.

    Return a new sorted list with the same elements as list1.

    This function should be recursive.
    """
    if len(list1) == 1:
        return list1
    elif len(list1) == 0:
        return []
    else:
        part1_list1 = list1[:len(list1)/2]
        part2_list1 = list1[len(list1)/2:]
        sort1_list1 = merge_sort(part1_list1)
        sort2_list1 = merge_sort(part2_list1)
        return merge(sort1_list1,sort2_list1)

# Function to generate all strings for the word wrangler game

def gen_all_strings(word):
    """
    Generate all strings that can be composed from the letters in word
    in any order.

    Returns a list of all strings that can be formed from the letters
    in word.

    This function should be recursive.
    """
    if len(word) == 1:
        return ['',word]
    elif len(word) == 0:
        return ['']
    else:
        first = word[0]
        rest = word[1:]
        rest_strings = gen_all_strings(rest)
        rest_strings2 = []
        for index_i in range(len(rest_strings)):
            item = rest_strings[index_i]
            for index_j in range(len(item)):
                rest_strings2.append(item[:index_j]+first+item[index_j:])
            rest_strings2.append(item+first)
        return rest_strings+rest_strings2

# Function to load words from a file

def load_words(filename):
    """
    Load word list from the file named filename.

    Returns a list of strings.
    """
    a_file = urllib2.urlopen(codeskulptor.file2url(filename))
    return a_file.readlines()

def run():
    """
    Run game.
    """
    words = load_words(WORDFILE)
    wrangler = provided.WordWrangler(words, remove_duplicates, 
                                     intersect, merge_sort, 
                                     gen_all_strings)
    provided.run_game(wrangler)

# Uncomment when you are ready to try the game
# run()

    
#################################################
###### test on remove_duplicates(list1)
# print 'test on remove_duplicates(list1)'
# list1 = ['a','b','b','cat','dog','dog','dpacd','efg','efgh','fucll','fucll']
# print remove_duplicates(list1)
###### test on intersect(list1, list2)
# print 'test on intersect(list1, list2)'
# str1 = 'dog'
# str2 = 'doh'
# if str1 == str2:
#     print 1
# elif str1 < str2:
#     print 2
# elif str1 > str2:
#     print 3
# else:
#     print 4
# list1 = ['aa','an','bcd','cat','dogdog','dppp','gabi']
# list2 = ['baby','cat','dog','dppp','eeee','fucll','gabi']
# print intersect(list1,list2)
###### test on merge(list1, list2)
# print 'test on merge(list1, list2)'
# list1 = ['aa','an','bcd','cat','dogdog','dppp','gabi']
# list2 = ['baby','cat','dog','dppp','eeee','fucll','gabi']
# print merge(list1,list2)
# list1 = ['aa','an']
# list2 = ['baby','cat','gabi']
# print merge(list1,list2)
###### test on merge_sort(list1)
# print 'test on merge_sort(list1)'
# list1 = ['eeee','fucll','aa','an','bcd','cat','dogdog','dppp','gabi']
# print merge_sort(list1)
###### test on gen_all_strings(word)
# print 'test on gen_all_strings(word)'
# word = 'a'
# print gen_all_strings(word)
# word = 'ab'
# print gen_all_strings(word)
# word = 'aa'
# print gen_all_strings(word)
###### test on load_words(filename)
# print 'test on load_words(filename)'
# words = load_words(WORDFILE)
# print type(words)
# print words
#################################################