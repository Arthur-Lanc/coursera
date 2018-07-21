"""
Provided code for Application portion of Module 1

Imports physics citation graph 
"""
import urllib2
import codeskulptor
import simpleplot
import math
from collections import Counter
import random
codeskulptor.set_timeout(20)

EX_GRAPH0 = {
                0: set([1,2]),
                1: set([]),
                2: set([])
            }
EX_GRAPH1 = {
                0: set([1,4,5]),
                1: set([2,6]),
                2: set([3]),
                3: set([0]),
                4: set([1]),
                5: set([2]),
                6: set([])
            }
EX_GRAPH2 = {
                0: set([1,4,5]),
                1: set([2,6]),
                2: set([3,7]),
                3: set([7]),
                4: set([1]),
                5: set([2]),
                6: set([]),
                7: set([3]),
                8: set([1,2]),
                9: set([0,3,4,5,6,7])
            }
STANDARD = True
LOGLOG = False
CITATION_URL = "http://storage.googleapis.com/codeskulptor-alg/alg_phys-cite.txt"

def load_graph(graph_url):
    """
    Function that loads a graph given the URL
    for a text representation of the graph
    
    Returns a dictionary that models a graph
    """
    graph_file = urllib2.urlopen(graph_url)
    graph_text = graph_file.read()
    graph_lines = graph_text.split('\n')
    graph_lines = graph_lines[ : -1]
    
    print "Loaded graph with", len(graph_lines), "nodes"
    
    answer_graph = {}
    for line in graph_lines:
        neighbors = line.split(' ')
        node = int(neighbors[0])
        answer_graph[node] = set([])
        for neighbor in neighbors[1 : -1]:
            answer_graph[node].add(int(neighbor))

    return answer_graph

def compute_in_degrees(digraph):
    '''
    compute_in_degrees
    '''
    result_dic = {}
    dig_key_list = digraph.keys()
    dig_val_list = digraph.values()
    dig_val_list2 = []
    for item1 in dig_val_list:
        dig_val_list2.extend(list(item1))
    dig_val_list_c_d = dict(Counter(dig_val_list2))
    for key_item in dig_key_list:
        if key_item in dig_val_list_c_d:
            result_dic[key_item] = dig_val_list_c_d[key_item]
        else:
            result_dic[key_item] = 0
    return result_dic

def in_degree_distribution(digraph):
    '''
    in_degree_distribution
    '''
    result_dic = {}
    comp_in_deg_dic = compute_in_degrees(digraph)
    cidd_list = comp_in_deg_dic.values()
    cidd_list_c_d = dict(Counter(cidd_list))
    key_list = sorted(list(set(cidd_list)))
    for key_item in key_list:
        result_dic[key_item] = cidd_list_c_d[key_item]
    return result_dic

def in_degree_distribution_normalized(digraph):
    result_dic = {}
    idd_dic = in_degree_distribution(digraph)
    all_node_num = len(digraph.keys())
    for key,value in idd_dic.items():
        result_dic[key] = float(value)/float(all_node_num)
    return result_dic

def build_plot(iddn_dic, plot_type = STANDARD):
    """
    build_plot
    """
    plot = []
    for key,value in iddn_dic.items():
        if plot_type == STANDARD:
            plot.append([key, value])
        else:
            plot.append([math.log(key), math.log(value)])
    return plot

def make_complete_graph(num_nodes):
    '''
    make_complete_graph
    '''
    result_dic = {}
    node_list = [item for item in range(num_nodes)]
    node_set = set(node_list)
    if num_nodes > 0:
        for i_item in range(num_nodes):
            temp_set = node_set.copy()
            temp_set.remove(i_item)
            result_dic[i_item] = temp_set
        return result_dic
    else:
        result_dic = {}
        return result_dic

def alg_er(num_nodes,prob_p):
    result_dic = {}
    if num_nodes > 0:
        for i_item in range(num_nodes):

            temp_set = set()
            for j_item in range(num_nodes):
                if i_item != j_item and random.random() < prob_p:
                    temp_set.add(j_item)

            result_dic[i_item] = temp_set
        return result_dic
    else:
        result_dic = {}
        return result_dic

def avg_out_degree(digraph):
    all_node_num = len(digraph.keys())
    temp_list = []
    for i_set in digraph.values():
        temp_list.append(len(i_set))
    return int(round(sum(temp_list)/float(all_node_num)))

class DPATrial:
    """
    Simple class to encapsulate optimized trials for DPA algorithm
    
    Maintains a list of node numbers with multiple instances of each number.
    The number of instances of each node number are
    in the same proportion as the desired probabilities
    
    Uses random.choice() to select a node number from this list for each trial.
    """

    def __init__(self, num_nodes):
        """
        Initialize a DPATrial object corresponding to a 
        complete graph with num_nodes nodes
        
        Note the initial list of node numbers has num_nodes copies of
        each node number
        """
        self._num_nodes = num_nodes
        self._node_numbers = [node for node in range(num_nodes) for dummy_idx in range(num_nodes)]


    def run_trial(self, num_nodes):
        """
        Conduct num_node trials using by applying random.choice()
        to the list of node numbers
        
        Updates the list of node numbers so that the number of instances of
        each node number is in the same ratio as the desired probabilities
        
        Returns:
        Set of nodes
        """
        
        # compute the neighbors for the newly-created node
        new_node_neighbors = set()
        for dummy_idx in range(num_nodes):
            new_node_neighbors.add(random.choice(self._node_numbers))
        
        # update the list of node numbers so that each node number 
        # appears in the correct ratio
        self._node_numbers.append(self._num_nodes)
        self._node_numbers.extend(list(new_node_neighbors))
        
        #update the number of nodes
        self._num_nodes += 1
        return new_node_neighbors

def alg_dpa(num_nodes,int_m):
    complete_graph = make_complete_graph(int_m)
    dpa_trial_obj = DPATrial(int_m)
    for i_node in range(int_m,num_nodes):
        v_t_set = dpa_trial_obj.run_trial(int_m)
        complete_graph[i_node] = v_t_set
    return complete_graph

plot_type = LOGLOG
#plot_type = STANDARD

#question1
citation_graph = load_graph(CITATION_URL)
iddn_dic = in_degree_distribution_normalized(citation_graph)
plot1 = build_plot(iddn_dic, plot_type)
simpleplot.plot_scatter("in-degree distribution", 600, 600, 
                      "in_degree(log)", "distribution_frac(log)", [plot1],['citation graph'])


# er_graph = alg_er(10,0.5)
#print er_graph
#print compute_in_degrees(er_graph)
# iddn_dic2 = in_degree_distribution_normalized(er_graph)
# plot2 = build_plot(iddn_dic2, plot_type)
# simpleplot.plot_lines("in-degree distribution", 600, 600, 
#                       "in_degree(log)", "distribution_frac(log)", [plot2],False,['ER graph'])

#question2
# 1.yes,the expected value of the in-degree for each node is n*p.(number of nodes:n,probability:p)
# 2.Normal distribution
# 3.they are difference.citation graph is slope down and converge at a limit number,but ER graph is plot like Normal distribution

#question3
#print avg_out_degree(citation_graph)
# n=27770
# m=13

#question4
# dpa_graph = alg_dpa(27770,13)
# iddn_dic3 = in_degree_distribution_normalized(dpa_graph)
# plot3 = build_plot(iddn_dic3, plot_type)
# simpleplot.plot_scatter("in-degree distribution", 600, 600, 
#                       "in_degree(log)", "distribution_frac(log)", [plot3],['DPA graph'])

#question5
# 1.Similar,both graph slope down first and then converge.
# 2.Hierarchical structure of networks.In hierarchical models,nodes with more links are expected to have a lower clustering coefficient.
# 3.a physics paper cited by several area are highly unlikely to connect with the same group nodes which in general results in a decreasing coefficient as the number of in-degree grows.