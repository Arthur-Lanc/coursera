pollutantmean <- function(directory,pollutant,id = 1:332){
    file_names <- paste(directory,sprintf("%03d.csv", id),sep="\\")
    csv_data_frame <- do.call(rbind,lapply(file_names,read.csv))
    mean(csv_data_frame[[pollutant]],na.rm = TRUE)
}



# dir <- 'C:\\Users\\Arthur-Lance\\Desktop\\rprogram\\week2'
# setwd(dir)

# directory <- 'specdata'
# pollutant <- 'sulfate'
# id <- 1:10

# paste(directory,sprintf("%03d.csv", id),sep="\\")
# setwd(directory)
# file_names = sprintf("%03d.csv", id)
# csv_data_frame <- do.call(rbind,lapply(file_names,read.csv))
# mean(csv_data_frame[[pollutant]],na.rm = TRUE)
# temp = list.files(pattern="*.csv")
# for (i in 1:length(temp)) {
#     assign(temp[i], read.csv(temp[i]))
# }
