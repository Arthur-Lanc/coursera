complete <- function(directory,id = 1:332){
	file_names <- paste(directory,sprintf("%03d.csv", id),sep="\\")
	csv_data_frame <- do.call(rbind,lapply(file_names,read.csv))
	label <- !is.na(csv_data_frame[[2]]) & !is.na(csv_data_frame[[3]])
	csv_data_frame_tf <- cbind(csv_data_frame,data.frame(label))
	csv_data_frame_tf_cut <- csv_data_frame_tf[csv_data_frame_tf$label == TRUE,]
	result_df <- data.frame(table(csv_data_frame_tf_cut$ID))
	colnames(result_df) <- c("id", "nobs")
	result_df_v2 <- result_df[match(id, result_df$id),]
	result_df_v2$id <- id
	result_df_v2[is.na(result_df_v2$nobs),"nobs"] <- 0
	rownames(result_df_v2) <- NULL
	result_df_v2
}






# dir <- 'C:\\Users\\Arthur-Lance\\Desktop\\rprogram\\week2'
# setwd(dir)

# directory <- 'specdata'
# id <- 287:290

# file_names <- paste(directory,sprintf("%03d.csv", id),sep="\\")
# csv_data_frame <- do.call(rbind,lapply(file_names,read.csv))
# label <- !is.na(csv_data_frame[[2]]) & !is.na(csv_data_frame[[3]])
# csv_data_frame_tf <- cbind(csv_data_frame,data.frame(label))
# csv_data_frame_tf_cut <- csv_data_frame_tf[csv_data_frame_tf$label == TRUE,]
# result_df <- data.frame(table(csv_data_frame_tf_cut$ID))
# colnames(result_df) <- c("id", "nobs")
# result_df_v2 <- result_df[match(id, result_df$id),]
# result_df_v2$id <- id
# result_df_v2[is.na(result_df_v2$nobs),"nobs"] <- 0
# rownames(result_df_v2) <- NULL