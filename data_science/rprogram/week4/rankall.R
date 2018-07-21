rankall <- function(outcome, num = "best") {
	## Read outcome data
	outcomecm <- read.csv("ProgAssignment3-data\\outcome-of-care-measures.csv", colClasses = "character")
	## Check that state and outcome are valid
	if (!(outcome %in% c("heart attack","heart failure","pneumonia"))){
	    stop("invalid outcome")
	}
	## For each state, find the hospital of the given rank
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
	unique_state <- sort(unique(outcomecm$State))
	hospital_vec <- character()
	for (state in unique_state){
		subset1 <- subset(outcomecm,(!is.na(outcomecm[,colnum])) & State == state,select = c(names(outcomecm)[2],colname))
		subset2 <- subset1[order(subset1[,2],subset1[,1]),]
		rownumber <- nrow(subset2)
		if (class(num) == "character"){
			if (num == "best"){
			    result <- subset2[1,1]
			} else if (num == "worst"){
			    result <- subset2[rownumber,1]
			}
		} else if (class(num) == "numeric"){
			if (num>rownumber){
				result <- NA
			} else if (num<1){
				result <- NA
			} else {
				result <- subset2[num,1]
			}
		}
		hospital_vec <- c(hospital_vec,result)
	}
	## Return a data frame with the hospital names and the
	## (abbreviated) state name
	result_df <- data.frame(hospital = hospital_vec, state = unique_state)
	row.names(result_df) <- unique_state
	result_df
}


# setwd('C:\\Users\\Administrator\\Documents\\coursera\\data_science\\rprogram\\week4')
# outcome<-"heart attack"
# num<-20


# ## Read outcome data
# outcomecm <- read.csv("ProgAssignment3-data\\outcome-of-care-measures.csv", colClasses = "character")
# ## Check that state and outcome are valid
# if (!(outcome %in% c("heart attack","heart failure","pneumonia"))){
#     stop("invalid outcome")
# }
# ## For each state, find the hospital of the given rank
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
# unique_state <- sort(unique(outcomecm$State))
# hospital_vec <- character()
# for (state in unique_state){
# 	subset1 <- subset(outcomecm,(!is.na(outcomecm[,colnum])) & State == state,select = c(names(outcomecm)[2],colname))
# 	subset2 <- subset1[order(subset1[,2],subset1[,1]),]
# 	rownumber <- nrow(subset2)
# 	if (class(num) == "character"){
# 		if (num == "best"){
# 		    result <- subset2[1,1]
# 		} else if (num == "worst"){
# 		    result <- subset2[rownumber,1]
# 		}
# 	} else if (class(num) == "numeric"){
# 		if (num>rownumber){
# 			result <- NA
# 		} else if (num<1){
# 			result <- NA
# 		} else {
# 			result <- subset2[num,1]
# 		}
# 	}
# 	hospital_vec <- c(hospital_vec,result)
# }
# ## Return a data frame with the hospital names and the
# ## (abbreviated) state name
# result_df <- data.frame(hospital = hospital_vec, state = unique_state)
# row.names(result_df) <- unique_state
# result_df


