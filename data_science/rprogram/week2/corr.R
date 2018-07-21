corr <- function(directory,threshold = 0){
	complete_df <- complete(directory)
	id <- complete_df[complete_df$nobs > threshold,"id"]
	file_names <- paste(directory,sprintf("%03d.csv", id),sep="\\")
	result_num_vec <- numeric()
	if (length(id) > 0){
		for (idx in 1:length(id)){
			temp_df <- read.csv(file_names[idx])
			temp_df_v2 <- temp_df[!is.na(temp_df$sulfate) & !is.na(temp_df$nitrate),]
			result_num_vec <- append(result_num_vec,cor(temp_df_v2$sulfate,temp_df_v2$nitrate))
		}
	}
	result_num_vec
}






# dir <- 'C:\\Users\\Arthur-Lance\\Desktop\\rprogram\\week2'
# setwd('C:\\Users\\Arthur-Lance\\Desktop\\rprogram\\week2')

# source("complete.R")
# directory <- 'specdata'
# threshold <- 0

# complete_df <- complete(directory)
# id <- complete_df[complete_df$nobs > threshold,"id"]
# file_names <- paste(directory,sprintf("%03d.csv", id),sep="\\")
# result_num_vec <- numeric()
# if (length(id) > 0){
# 	for (idx in 1:length(id)){
# 		temp_df <- read.csv(file_names[idx])
# 		temp_df_v2 <- temp_df[!is.na(temp_df$sulfate) & !is.na(temp_df$nitrate),]
# 		result_num_vec <- append(result_num_vec,cor(temp_df_v2$sulfate,temp_df_v2$nitrate))
# 	}
# }
# result_num_vec




