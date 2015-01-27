---
layout: global
title: Examples--
---

This page describes how to implement an algorithm with [OctMatrix API](API-Docs.html) and how to use the [ML-Library](ML-Library.html).

# Examples for OctMatrix API

If you are familiar with R-Matrix or have already implemented algorithms with R-Matrix, it will be very easy for you to implement an algorithm with OctMatrix, since OctMatrix provides the similar API with R-Matrix. Here we take Logistic Regression(LR) and K-Means for instance.

## Logistic Regression(LR)

Logistic Regression(LR) is a typical probabilistic statistical classification model. We usually use it to predict the outcome of a categorical dependent variable (i.e., a class label) based on one or more predictor variables/features. As a classification model, LR can be split into two parts, training and predicting. Here we implement LR as follows:

-    When training, we take sigmod as the logistic function and iteratively calculate the model parameters with gradient descent.
-    When predicting, we apply the model on the observations and classify them according to the results.

With OctMatrix, we can implement LR with the representation of matrix:

    # Train a logistic regression classifier with hinge loss. Return the weights.
    # x: an OctMatrix, where each row represents an observation.
    # y: an OctMatrix, where each row represents the label of an observation.
    # iters: number of iterations.
    # stepSize: the step for gradient.
    train.lr <- function(x, y, iters, stepSize) {
      # Initialize paramaters.
      dims <- dim(x)
      m <- dims[1]
      n <- dims[2]
      x <- cbind2(ones(m, 1), x)
      theta <- zeros(n+1, 1)
      
      # Define the sigmod function.
      g <- function(z) {
        1.0 / (1.0 + exp(-z)) 
      }
      
      # Update the model parameters iteratively with gradient descent.
      for (i in 1:iters) {
        z <- x %*% theta
        h <- apply(z, c(1,2), g)
        grad <- t(x) %*% (h - y)
        theta <- theta - stepSize / sqrt(i) * grad
      }
      theta
    }

<br/>

    # Predict data x with a logistic regression classifier. Return the labels.
    # model: the logistic regression classifier model, which is the weights returned by train.lr(...).
    # x: an OctMatrix, where each row represents an observation.
    predict.lr <- function(model, x) {
      # Define the sigmod function.
      g <- function(z) {
        1.0 / (1.0 + exp(-z)) 
      }
      
      # Apply the model on the observations.
      data <- cbind2(ones(dim(x)[1], 1), x)
      margin <- data %*% model
      
      # Classify into 2 classes, (< 0.5 ? 0 : 1).
      apply(margin, c(1,2), function(z){
        ifelse (g(z) < 0.5, 0.0, 1.0)
      })
    }

## K-Means

K-Means is a popular clustering algorithm in data mining. It aims to partition `n` observations into `k` clusters in which each observation belongs to the cluster with the nearest mean. The basic steps of K-Means are:

-    Choose `k` cluster centers.
-    In each iteration, calculate the distance between each observation and each center and choose the nearest one.
-    Update the centers with the new means.
-    Terminate if reach the max iterations or the centers become stable.

With OctMatrix, we can implement K-Means with the representation of matrix:

    # Train a k-means model. Return two OctMatrix, 'centers' and 'vector'.  'centers' represents the coordinates of each center and 'vector' is a one-column OctMatrix of integers (from 1:k) indicating the cluster to which each point is allocated.
    # data: an OctMatrix, where each row represents an observation.
    # clusters: num of clusters.
    # max_iter: the maximum number of iterations.
    kmeans.OctMatrix <- function(data, clusters, max_iter = 3)
    {
      # Preparations, initialize the k centers
      train_num <- dim(data)[1]
      raw_data <- t(data)
      centers <- t(data[1:clusters,])
      
      # Iterations
      for(i in 1: max_iter)
      {
        # Calculate the distance with (x-y)^2=x^2+y^2-2xy
        x <- rep(apply(t(raw_data * raw_data), 1, sum), clusters)
        y <- t(rep(apply(t(centers * centers), 1, sum), train_num))
        dis <- x + y - 2 * (t(raw_data) %*% centers)
        
        # Choose the nearest center and split data to different clusters
        tmp <- apply(dis, 1, which.min)
        ls <- split(data, tmp)
        
        # Update centers with new means
        ls2 <- lapply(ls, function(x) (apply(t(x), 1, sum) / dim(x)[1]))
        ls3 <- ls2[[1]]
        if(length(ls2) > 1)
        {
          # Since we only have cbind2, use iterations to bind the new centers
          for(i in 2:length(ls2))
            ls3 <- cbind2(ls3, ls2[[i]])
        }
        
        # If not enough centers, choose from the origin data
        if(length(ls3) < clusters)
          centers <- cbind2(ls3, t(data[(length(ls2) + 1):clusters,]))
        else
          centers <- ls3
      }
      
      # Get the final cluster of each observation
      x <- rep(apply(t(raw_data * raw_data), 1, sum), clusters)
      y <- t(rep(apply(t(centers * centers), 1, sum), train_num))
      dis <- x + y - 2 * (t(raw_data) %*% centers)
      tmp <- apply(dis, 1, which.min)
      
      list("vector" = tmp, "centers" = t(centers))
    }

# Examples for ML-Library

Octopus' ML-Library already provides several useful algorithms, including classification, regression, clustering and feature extraction. For an application, you only need to complete the input and output steps, e.g. to generate the input OctMatrix with yout input data and to convert the output OctMatrix to your own data structure.

## K-Means

Here we give an example on clustering with K-Means algorithm and iris data set. This famous (Fisher's or Anderson's) iris data set gives the measurements in centimeters of the variables sepal length and width and petal length and width, respectively, for 50 flowers from each of 3 species of iris. The species are Iris setosa, versicolor, and virginica. The dataset is like this:

    #   Sepal.Length Sepal.Width Petal.Length Petal.Width Species
    #           5.1         3.5          1.4         0.2  setosa
    #           4.9         3.0          1.4         0.2  setosa
    #           4.7         3.2          1.3         0.2  setosa
    #           4.6         3.1          1.5         0.2  setosa
    #           5.0         3.6          1.4         0.2  setosa
    #           5.4         3.9          1.7         0.4  setosa
    #...

The R script is very simple since we already have the K-Means algorithm:

    # Environment initialization.
    library(ggplot2)
    require(OctMatrix)
    source(paste(OCTOPUS_HOME, "/R/ml-library/kmeans.R", sep = ""))
    
    engineType = "Spark"    # Choose the platform as your wish.
    
    # Load iris data.
    data(iris)
     
    # Remove orginal labels.
    data <- as.matrix(iris[, -dim(iris)[2]])
    
    # Generate OctMatrix with the input data
    data <- OctMatrix(data, engineType = engineType)
    
    # Call the kmeans function.
    res <- kmeans.OctMatrix(data, clusters = 3, max_iter = 5)
     
    # Show the results.
    table(iris[, ncol(iris)], as.vector(as.matrix(res$vector)))

