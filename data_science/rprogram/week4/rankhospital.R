rankhospital <- function(state, outcome, num = "best") {
	## Read outcome data
	outcomecm <- read.csv("ProgAssignment3-data\\outcome-of-care-measures.csv", colClasses = "character")
	## Check that state and outcome are valid
	if (!(state %in% outcomecm$State)){
	    stop("invalid state")
	}
	if (!(outcome %in% c("heart attack","heart failure","pneumonia"))){
	    stop("invalid outcome")
	}
	## Return hospital name in that state with the given rank
	## 30-day death rate
	if (outcome == "heart attack"){
	    colname <- names(outcomecm)[11]
	    colnum <- 11
	} else if (outcome == "heart failure"){
	    colname <- names(outcomecm)[17]
	    colnum <- 17
	} else if (outcome == "pneumonia"){
	    colname <- names(outcomecm)[23]
	    colnum <- 23
	}
	outcomecm[,colnum] <- as.numeric(outcomecm[,colnum])
	subset1 <- subset(outcomecm,(!is.na(outcomecm[,colnum])) & State == state,select = c(names(outcomecm)[2],colname))
	subset2 <- subset1[order(subset1[,2],subset1[,1]),]
	rownumber <- nrow(subset2)
	if (class(num) == "character"){
		if (num == "best"){
		    return(subset2[1,1])
		} else if (num == "worst"){
		    return(subset2[rownumber,1])
		}
	} else if (class(num) == "numeric"){
		if (num>rownumber){
			return(NA)
		} else if (num<1){
			return(NA)
		} else {
			return(subset2[num,1])
		}
	}
}


# setwd('C:\\Users\\Administrator\\Documents\\coursera\\data_science\\rprogram\\week4')
# state<-"TX"
# outcome<-"heart failure"
# num<-4


# ## Read outcome data
# outcomecm <- read.csv("ProgAssignment3-data\\outcome-of-care-measures.csv", colClasses = "character")
# ## Check that state and outcome are valid
# if (!(state %in% outcomecm$State)){
#     stop("invalid state")
# }
# if (!(outcome %in% c("heart attack","heart failure","pneumonia"))){
#     stop("invalid outcome")
# }
# ## Return hospital name in that state with the given rank
# ## 30-day death rate
# if (outcome == "heart attack"){
#     colname <- names(outcomecm)[11]
#     colnum <- 11
# } else if (outcome == "heart failure"){
#     colname <- names(outcomecm)[17]
#     colnum <- 17
# } else if (outcome == "pneumonia"){
#     colname <- names(outcomecm)[23]
#     colnum <- 23
# }
# outcomecm[,colnum] <- as.numeric(outcomecm[,colnum])
# subset1 <- subset(outcomecm,(!is.na(outcomecm[,colnum])) & State == state,select = c(names(outcomecm)[2],colname))
# subset2 <- subset1[order(subset1[,2],subset1[,1]),]
# rownumber <- nrow(subset2)
# if (class(num) == "character"){
# 	if (num == "best"){
# 	    return(subset2[1,1])
# 	} else if (num == "worst"){
# 	    return(subset2[rownumber,1])
# 	}
# } else if (class(num) == "numeric"){
# 	if (num>rownumber){
# 		return(NA)
# 	} else if (num<1){
# 		return(NA)
# 	} else {
# 		return(subset2[num,1])
# 	}
# }



