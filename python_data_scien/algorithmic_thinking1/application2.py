import random
import math
import alg_upa_trial
import alg_application2_provided
import project2
import matplotlib.pyplot as plt
import time

def u_er(n,p):
    v = set([i for i in range(n)])
    e = set([])
    for i in range(n):
        for j in range(n):
            if i < j:
                a = random.random()
                if a < p:
                    e.add(frozenset({i,j}))
    g = (v,e)
    return g

def u_graph_to_dictionary(g):
    v = g[0]  
    e = g[1]
    result_dict = {}
    for i in v:
        result_dict[i] = set([])

    for item in e:
        temp_list = list(item)
        i = temp_list[0]
        j = temp_list[1]
        result_dict[i].add(j)
        result_dict[j].add(i)
    return result_dict

def dictionary_to_u_graph(result_dict):
    v = set(result_dict.keys())
    e = set([])
    for i,value in result_dict.items():
        for j in value:
            e.add(frozenset({i,j}))
    return (v,e)
    
def make_complete_ugraph(n):
    '''
    make_complete_ugraph
    '''
    v = set([i for i in range(n)])
    e = set([])
    for i in range(n):
        for j in range(n):
            if i < j:
                e.add(frozenset({i,j}))
    g = (v,e)
    return g

def upa(n,m):
    g = make_complete_ugraph(m)
    v = g[0]
    e = g[1]
    upa_trial_obj = alg_upa_trial.UPATrial(m)
    for i in range(m,n):
        v.add(i)
        v_t_set = upa_trial_obj.run_trial(m)
        for j in v_t_set:
            e.add(frozenset({i,j}))
    return (v,e)

def random_order(graph_tuple):
    v = graph_tuple[0]
    v_list = list(v)
    random.shuffle(v_list)
    return v_list

def legend_example(p,m,node,comput_res_list,er_res_list,upa_res_list):
    """
    Plot an example with two curves with legends
    """
    xvals = [i for i in range(0,node+1)]

    plt.plot(xvals, comput_res_list, '-b', label='computer network graph')
    plt.plot(xvals, er_res_list, '-r', label='er graph(p:%s)' % str(p))
    plt.plot(xvals, upa_res_list, '-y', label='upa graph(m:%s)' % str(m))
    plt.legend(loc='upper right')
    plt.xlabel('removed node num')
    plt.ylabel('largest cc size')
    plt.title('compute_resilience')
    plt.grid(True)
    plt.show()

def fasttargetedorder(graph_dict):
    graph_dict_copy = alg_application2_provided.copy_graph(graph_dict)

    n = len(graph_dict_copy.keys())
    degreesets = [i for i in range(n)]
    for k in range(n):
        degreesets[k] = set()
    for i in range(n):
        d = len(graph_dict_copy[i])
        degreesets[d].add(i)
    l = []
    for k in range(n-1,-1,-1):
        while len(degreesets[k]) != 0:
            u = degreesets[k].pop()
            for v in graph_dict_copy[u]:
                d = len(graph_dict_copy[v])
                degreesets[d].remove(v)
                degreesets[d-1].add(v)
            l.append(u)
            alg_application2_provided.delete_node(graph_dict_copy, u)
    return l

def question3_plot():
    upa_graph_dict_list = []
    m = 5
    for n in range(10, 1000, 10):
        upa_graph_tuple = upa(n,m)
        upa_graph_dict = u_graph_to_dictionary(upa_graph_tuple)
        upa_graph_dict_list.append(upa_graph_dict)

    xvals = [n for n in range(10, 1000, 10)]

    targeted_order_yvals = []
    for n in range(len(xvals)):
        start = time.clock()
        alg_application2_provided.targeted_order(upa_graph_dict_list[n])
        elapsed = (time.clock() - start)
        targeted_order_yvals.append(elapsed)

    fasttargetedorder_yvals = []
    for n in range(len(xvals)):
        start = time.clock()
        fasttargetedorder(upa_graph_dict_list[n])
        elapsed = (time.clock() - start)
        fasttargetedorder_yvals.append(elapsed)

    plt.plot(xvals, targeted_order_yvals, '-b', label='targeted_order')
    plt.plot(xvals, fasttargetedorder_yvals, '-r', label='fasttargetedorder')
    plt.legend(loc='upper right')
    plt.xlabel('node num')
    plt.ylabel('running times')
    plt.title('desktop Python')
    plt.grid(True)
    plt.show()

node = 1239
edge = 3047
p = float(3047)/float(766941)
m = int((float(edge)/float(node)))

comput_graph_dict = alg_application2_provided.load_graph(alg_application2_provided.NETWORK_URL)
#comput_graph_tuple = dictionary_to_u_graph(comput_graph_dict)
#comput_res_list = project2.compute_resilience(comput_graph_dict,random_order(comput_graph_tuple))
comput_res_list2 = project2.compute_resilience(comput_graph_dict,alg_application2_provided.targeted_order(comput_graph_dict))

er_graph_tuple = u_er(node,p)
er_graph_dict = u_graph_to_dictionary(er_graph_tuple)
#er_res_list = project2.compute_resilience(er_graph_dict,random_order(er_graph_tuple))
er_res_list2 = project2.compute_resilience(er_graph_dict,alg_application2_provided.targeted_order(er_graph_dict))

upa_graph_tuple = upa(node,m)
upa_graph_dict = u_graph_to_dictionary(upa_graph_tuple)
#upa_res_list = project2.compute_resilience(upa_graph_dict,random_order(upa_graph_tuple))
upa_res_list2 = project2.compute_resilience(upa_graph_dict,alg_application2_provided.targeted_order(upa_graph_dict))



#Question 1 (5 pts)
#legend_example(p,m,node,comput_res_list,er_res_list,upa_res_list)
#Question 2 (1 pt)
#all three graphs are resilient under random attacks as the first 20% of their nodes are removed.
#question 3
# targeted_order : O(n**2)
# fast_targeted_order : O(n)
#question3_plot()
#question 4
legend_example(p,m,node,comput_res_list2,er_res_list2,upa_res_list2)
#Question 5 (1 pt)
#er graph is resilient under targeted attacks as the first 20% of their nodes are removed
#Question 6
#cost,may be it is very expensive to model a random network.

#####################################
# graph = u_er(4,0.5)
# r = u_graph_to_dictionary(graph)
# print r
# graph = make_complete_ugraph(node)
# # r = u_graph_to_dictionary(graph)
# # print graph
# # print r
# print len(graph[0])
# print len(graph[1])

# graph = upa(5,3)
# r = u_graph_to_dictionary(graph)
# print r
# print dictionary_to_u_graph(r)