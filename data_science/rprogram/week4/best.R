best <- function(state, outcome) {
    ## Read outcome data
    outcomecm <- read.csv("ProgAssignment3-data\\outcome-of-care-measures.csv", colClasses = "character")
    ## Check that state and outcome are valid
    if (!(state %in% outcomecm$State)){
        stop("invalid state")
    }
    if (!(outcome %in% c("heart attack","heart failure","pneumonia"))){
        stop("invalid outcome")
    }
    ## Return hospital name in that state with lowest 30-day death
    ## rate
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
    subset2[1,1]
}


# setwd('C:\\Users\\Administrator\\Documents\\coursera\\data_science\\rprogram\\week4')
# state<-"TX"
# outcome<-"heart attack"


# ## Read outcome data
# outcomecm <- read.csv("ProgAssignment3-data\\outcome-of-care-measures.csv", colClasses = "character")
# ## Check that state and outcome are valid
# if (!(state %in% outcomecm$State)){
#     stop("invalid state")
# }
# if (!(outcome %in% c("heart attack","heart failure","pneumonia"))){
#     stop("invalid outcome")
# }
# ## Return hospital name in that state with lowest 30-day death
# ## rate
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
# subset2[1,1]

