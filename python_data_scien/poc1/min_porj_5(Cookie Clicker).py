#http://www.codeskulptor.org/#user43_af51HBSufEWoOOq_2.py

"""
Cookie Clicker Simulator
"""

import simpleplot
import math

# Used to increase the timeout, if necessary
import codeskulptor
codeskulptor.set_timeout(20)

import poc_clicker_provided as provided

# Constants
SIM_TIME = 10000000000.0

class ClickerState:
    """
    Simple class to keep track of the game state.
    """
    
    def __init__(self):
        self._total_cookies = 0.0
        self._current_cookies = 0.0	
        self._current_time = 0.0
        self._current_cps = 1.0
        self._history_list = [(0.0, None, 0.0, 0.0)]
        
    def __str__(self):
        """
        Return human readable state
        """
        debug_str = '\n'
        debug_str += 'current_time:'+str(self._current_time)+'\n'
        debug_str += 'current_cookies:'+str(self._current_cookies)+'\n'
        debug_str += 'current_cps:'+str(self._current_cps)+'\n'
        debug_str += 'total_cookies:'+str(self._total_cookies)+'\n'
        debug_str += 'history_list:'+str(self._history_list)+'\n'
        return debug_str
        
    def get_cookies(self):
        """
        Return current number of cookies 
        (not total number of cookies)
        
        Should return a float
        """
        return float(self._current_cookies)
    
    def get_cps(self):
        """
        Get current CPS

        Should return a float
        """
        return float(self._current_cps)
    
    def get_time(self):
        """
        Get current time

        Should return a float
        """
        return float(self._current_time)
    
    def get_history(self):
        """
        Return history list

        History list should be a list of tuples of the form:
        (time, item, cost of item, total cookies)

        For example: [(0.0, None, 0.0, 0.0)]

        Should return a copy of any internal data structures,
        so that they will not be modified outside of the class.
        """
        return list(self._history_list)

    def time_until(self, cookies):
        """
        Return time until you have the given number of cookies
        (could be 0.0 if you already have enough cookies)

        Should return a float with no fractional part
        """
        if self._current_cookies >= cookies:
            time_until_seconds = 0.0
        else:
            time_until_seconds = float(math.ceil((cookies - self._current_cookies)/self._current_cps))
        return time_until_seconds
    
    def wait(self, time):
        """
        Wait for given amount of time and update state

        Should do nothing if time <= 0.0
        """
        if time > 0.0:
            self._current_time += time
            self._current_cookies += self._current_cps * time
            self._total_cookies += self._current_cps * time
    
    def buy_item(self, item_name, cost, additional_cps):
        """
        Buy an item and update state

        Should do nothing if you cannot afford the item
        """
        if self._current_cookies >= cost:
            self._current_cookies -= cost
            self._current_cps += additional_cps
            new_entry = (self._current_time,item_name,cost,self._total_cookies)
            self._history_list.append(new_entry)
   
    
def simulate_clicker(build_info, duration, strategy):
    """
    Function to run a Cookie Clicker game for the given
    duration with the given strategy.  Returns a ClickerState
    object corresponding to the final state of the game.
    """

    build_info_clone = build_info.clone()
    clickerstate_obj = ClickerState()
    while clickerstate_obj.get_time() <= duration:
        cookies = clickerstate_obj.get_cookies()
        cps = clickerstate_obj.get_cps()
        history = clickerstate_obj.get_history()
        time_left = duration - clickerstate_obj.get_time()
        item_to_purchase = strategy(cookies, cps, history, time_left, build_info_clone)
        if item_to_purchase == None:
            break
        
        elapse_time = clickerstate_obj.time_until(build_info_clone.get_cost(item_to_purchase))
        if elapse_time > time_left:
            break

        clickerstate_obj.wait(elapse_time)
        clickerstate_obj.buy_item(item_to_purchase, build_info_clone.get_cost(item_to_purchase), build_info_clone.get_cps(item_to_purchase))
        build_info_clone.update_item(item_to_purchase)

    time_left = duration - clickerstate_obj.get_time()
    if time_left > 0.0:
        clickerstate_obj.wait(time_left)
         
    return clickerstate_obj


def strategy_cursor_broken(cookies, cps, history, time_left, build_info):
    """
    Always pick Cursor!

    Note that this simplistic (and broken) strategy does not properly
    check whether it can actually buy a Cursor in the time left.  Your
    simulate_clicker function must be able to deal with such broken
    strategies.  Further, your strategy functions must correctly check
    if you can buy the item in the time left and return None if you
    can't.
    """
    return "Cursor"

def strategy_none(cookies, cps, history, time_left, build_info):
    """
    Always return None

    This is a pointless strategy that will never buy anything, but
    that you can use to help debug your simulate_clicker function.
    """
    return None

def strategy_cheap(cookies, cps, history, time_left, build_info):
    """
    Always buy the cheapest item you can afford in the time left.
    """
    build_items_list = build_info.build_items()
    cheapest_item_name = None
    for idx in range(len(build_items_list)):
        if build_info.get_cost(build_items_list[idx]) <= cookies + cps * time_left:
            if cheapest_item_name == None:
                cheapest_item_name = build_items_list[idx]
            else:
                if build_info.get_cost(build_items_list[idx]) < build_info.get_cost(cheapest_item_name):
                    cheapest_item_name = build_items_list[idx]
    return cheapest_item_name

def strategy_expensive(cookies, cps, history, time_left, build_info):
    """
    Always buy the most expensive item you can afford in the time left.
    """
    build_items_list = build_info.build_items()
    expensive_item_name = None
    for idx in range(len(build_items_list)):
        if build_info.get_cost(build_items_list[idx]) <= cookies + cps * time_left:
            if expensive_item_name == None:
                expensive_item_name = build_items_list[idx]
            else:
                if build_info.get_cost(build_items_list[idx]) > build_info.get_cost(expensive_item_name):
                    expensive_item_name = build_items_list[idx]
    return expensive_item_name

def strategy_best(cookies, cps, history, time_left, build_info):
    """
    The best strategy that you are able to implement.
    """
    build_items_list = build_info.build_items()
    max_cps_div_cost_item = None
    for idx in range(len(build_items_list)):
        if build_info.get_cost(build_items_list[idx]) <= cookies + cps * time_left:
            if max_cps_div_cost_item == None:
                max_cps_div_cost_item = build_items_list[idx]
            else:
                cps_div_cost_temp1 = build_info.get_cps(build_items_list[idx])/build_info.get_cost(build_items_list[idx])
                cps_div_cost_temp2 = build_info.get_cps(max_cps_div_cost_item)/build_info.get_cost(max_cps_div_cost_item)
                if cps_div_cost_temp1 > cps_div_cost_temp2:
                    max_cps_div_cost_item = build_items_list[idx]
    return max_cps_div_cost_item
        
def run_strategy(strategy_name, time, strategy):
    """
    Run a simulation for the given time with one strategy.
    """
    state = simulate_clicker(provided.BuildInfo(), time, strategy)
    print strategy_name, ":", state

    # Plot total cookies over time

    # Uncomment out the lines below to see a plot of total cookies vs. time
    # Be sure to allow popups, if you do want to see it

    history = state.get_history()
    history = [(item[0], item[3]) for item in history]
    simpleplot.plot_lines(strategy_name, 1000, 400, 'Time', 'Total Cookies', [history], True)

def run():
    """
    Run the simulator.
    """    
    #run_strategy("Cursor", SIM_TIME, strategy_cursor_broken)
    #run_strategy("Pointless", SIM_TIME, strategy_none)

    # Add calls to run_strategy to run additional strategies
    #run_strategy("Cheap", SIM_TIME, strategy_cheap)
    #run_strategy("Expensive", SIM_TIME, strategy_expensive)
    run_strategy("Best", SIM_TIME, strategy_best)
    
#run()
#strategy_cheap(2.0, 1.0, [(0.0, None, 0.0, 0.0)], 1.0, provided.BuildInfo({'A': [5.0, 1.0], 'C': [50000.0, 3.0], 'B': [500.0, 2.0]}, 1.15))
#provided.BuildInfo().get_cost('A')