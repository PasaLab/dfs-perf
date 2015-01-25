---
layout: global
title: ML-Library
---

Octopus provides a Machine Learning Library, which is built upon the OctMatrix package and contains several useful machine learning algorithms. This part describes what ML-Library contains and how to use them.

* Classification and regression
  * [Linear Regression](ML-Library.html#linear-regression)
  * [Logistic Regression](ML-Library.html#logistic-regression)
  * [Softmax](ML-Library.html#softmax)
  * [Linear Support Vector Machine (SVM)](ML-Library.html#linear-svm)
* Clustering
  * [K-Means](ML-Library.html#k-means)
* Feature extraction
  * [Deep Neural Network](ML-Library.html#deep-neural-network)

## Linear Regression

    source("OCTOPUS_HOME/R/ml-library/linear_regression.R")

There are two functions, one to train a linear regression model and another to calculate the regression value.

    linear_regression <- function(x, label)
    # Train a linear regression model: label = x %*% param, where param is an OctMatrix.
    # x: an OctMatrix, where each row represents an observation.
    # label: value of each observation.
    
<br/>

    predict.oct_lm_param <- function(model, x)
    # Calculate the regression value with a linear regression model. Return a one-column OctMatrix, where each row represents regression value of a observation.
    # model: a linear regression model trained by linear_regression(...).
    # x: an OctMatrix, where each row represents an observation.

See the linear regression application example in `$OCTOPUS_HOME/R/examples/app/app_lm_weight_height.R`

## Logistic Regression

    source("OCTOPUS_HOME/R/ml-library/lr.R")

There are two functions, one to train a logistic regression classifier model and another to predict.

    train.lr <- function(x, y, iters, stepSize)
    # Train a logistic regression classifier with hinge loss. Return the weights.
    # x: an OctMatrix, where each row represents an observation.
    # y: an OctMatrix, where each row represents the label of an observation.
    # iters: number of iterations.
    # stepSize: the step for gradient.

<br/>

    predict.lr <- function(model, x)
    # Predict data x with a logistic regression classifier. Return the labels.
    # model: the logistic regression classifier model, which is the weights returned by train.lr(...).
    # x: an OctMatrix, where each row represents an observation.
    
See the logistic regression application example in `$OCTOPUS_HOME/R/examples/app/lr.R`

## Softmax

    source("OCTOPUS_HOME/R/ml-library/softmax.R")
    
There are two functions, one to train a softmax classifier model and another to predict.

    softmax <- function(data, label, lambda = 0.0003, alpha = 0.1, max_iter = 1000, engineType = "R")
    # Train a softmax classifier. Return an OctMatrix which represents the weights.
    # data: an OctMatrix, where each row represents an observation.
    # label: a VECTOR, which is the true classifications of data.
    # max_iter: the maximum number of iterations.
    # alpha: learning rate of each iteration.
    # lambda: indicates how important the weight decay term is. Assuming the importance of lost function (e.g. 0.5 * (y - f(x))^2 ) is 1.
    # engine_type: platform to compute, which is one of "R", "Spark", "Mpi" and "Hadoop".
    
<br/>

    predict.oct_softmax_param <- function(model, data, engineType = "R")
    # Predict data x with a softmax classifier. Return two OctMatrix, 'vector' and 'matrix'.  'vector' represents the class of each observation belongs to and 'matrix' is the probability matrix.
    # model: the softmax classifier model trained by softmax(...).
    # data: an OctMatrix, where each row represents an observation.
    
See the softmax application example in `$OCTOPUS_HOME/R/examples/app/app_softmax_sentiment.R`
    
## Linear SVM

    source("OCTOPUS_HOME/R/ml-library/svm.R")
    
There are two functions, one to train a svm classifier model and another to predict.

    train.svm <- function(x, y, iters, stepSize)
    # Train a svm classifier with hinge loss. Return the weights.
    # x: an OctMatrix, where each row represents an observation.
    # y: an OctMatrix, where each row represents the label of an observation.
    # iters: number of iterations.
    # stepSize: the step for gradient.
    
<br/>

    predict.svm <- function(model, x)
    # Predict data x with a svm classifier. Return the labels.
    # model: the svm classifier model, which is the weights returned by train.svm(...).
    # x: an OctMatrix, where each row represents an observation.
    
See the svm application example in `$OCTOPUS_HOME/R/examples/app/svm.R`

## K-Means

    source("OCTOPUS_HOME/R/ml-library/kmeans.R")
    
There is one functions which trains a k-means model to calculate the centers of each cluster.

    kmeans.OctMatrix <- function(data, clusters, max_iter = 3)
    # Train a k-means model. Return two OctMatrix, 'centers' and 'vector'.  'centers' represents the coordinates of each center and 'vector' is a one-column OctMatrix of integers (from 1:k) indicating the cluster to which each point is allocated.
    # data: an OctMatrix, where each row represents an observation.
    # clusters: num of clusters.
    # max_iter: the maximum number of iterations.
    
See the k-means application example in `$OCTOPUS_HOME/R/examples/app/app_kmeans_iris.R`
    
## Deep Neural Network

    source("OCTOPUS_HOME/R/ml-library/deep_neural_network.R")
    
There are two functions, one to train a stacked-autoencoder model and another to extract features with the stacked-autoencoder.

    deep_neural_network <- function(data, nodes, output = "param", max_iter = 10, back_iter = 10, beta = 0.2, rho = 0.05, lambda = 0.003, alpha = 0.1, fun = "sigmod", supervised = "none", label = NULL, engine_type = "R")
    # Train a stacked-autoencoder. Return the model, which consists of weight item 'w' and bias item 'b', both length are 2 * layer num.
    # data: an OctMatrix, where each row represents an observation.
    # engine_type: platform to compute, which is one of "R", "Spark", "Mpi" and "Hadoop".
    # nodes: a vector, which represents the ith hidden layer has nodes[i] neuron(s).
    # max_iter: the maximum number of iterations during pre-training of each hidden-layer.
    # back_iter: the maximum number of iterations during fine-tuning using back propagation.
    # fun: activation function. Now only support "sigmod".
    # alpha: learning rate of each iteration.
    # lambda: indicates how important the weight decay term is. Assuming the importance of lost function (e.g. 0.5 * (y - f(x))^2 ) is 1.
    # beta: indicates how important the sparsity penalty term is. Assuming the importance of lost function (e.g. 0.5 * (y - f(x))^2 ) is 1.
    # rho: sparsity parameter, e.g. if I want 5% of neurons activated, then rho = 0.05.
    
<br/>

    predict.DNN_extractor <- function(model, data, output = "feature")
    # Extract features with a stacked-autoencoder model. Return an OctMatrix, where each row represents an observation.
    # model: the stacked-autoencoder model, which is trained by deep_neural_network(...).
    # data: an OctMatrix, where each row represents an observation.
    # output: what you want. "feature" means feature_extraction and "decode" means encode/decode.
    
See the deep nerual network application example in `$OCTOPUS_HOME/R/examples/app/app_dnn_mnist.R`
