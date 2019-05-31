import tensorflow as tf
from sklearn.model_selection import StratifiedKFold,train_test_split
import os
from pickle import load
from collections import Counter
import matplotlib.pyplot as plt
import time
import sklearn
import numpy as np
import pickle
from sklearn.metrics import accuracy_score, roc_auc_score, f1_score, recall_score, precision_score, roc_curve  # roc计算曲线
import xlwt
import pandas as pd
from tensorflow.python.ops import resources
from tensorflow.contrib.tensor_forest.python import tensor_forest


# 建模：tensorflow框架 逻辑回归方法
def RF_model(x_train, y_train,f):
    # 定义
    # feature_num = 232
    # sample_num =2390
    num_steps = 200  # Total steps to train
    batch_size = 300  # The number of samples per batch
    num_classes = 2  # The 10 digits
    num_features = 232  # Each image is 28x28 pixels
    num_trees = 200
    max_nodes = 120

    # Input and Target data
    x = tf.placeholder(tf.float32, shape=[None, num_features], name='x')
    # For random forest, labels must be integers (the class id)
    y = tf.placeholder(tf.int32, shape=[None, 1], name='y')

    # Random Forest Parameters
    hparams = tensor_forest.ForestHParams(num_classes=num_classes,
                                          num_features=num_features,
                                          num_trees=num_trees,
                                          max_nodes=max_nodes).fill()

    # Build the Random Forest
    forest_graph = tensor_forest.RandomForestGraphs(hparams)
    # Get training graph and loss
    train_op = forest_graph.training_graph(x, y)
    loss_op = forest_graph.training_loss(x, y)

    # Measure the accuracy
    infer_op, _, _ = forest_graph.inference_graph(x)
    infer = tf.cast(infer_op, tf.float16, name='z')
    # yp = tf.argmax(infer_op, 1, name='y_prediction')
    correct_prediction = tf.equal(tf.argmax(infer_op, 1), tf.cast(y, tf.int64))
    accuracy_op = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

    # Initialize the variables (i.e. assign their default value) and forest resources
    init_vars = tf.group(tf.global_variables_initializer(),
                         resources.initialize_resources(resources.shared_resources()))

    # Start TensorFlow session
    # sess = tf.train.MonitoredSession()
    # sess.run(init_vars)
    # for i in range(1, num_steps + 1):
    #     # Prepare Data
    #     # Get the next batch of MNIST data (only images are needed, not labels)
    #     # batch_x, batch_y = mnist.train.next_batch(batch_size)
    #     rand_index = np.random.choice(len(x_train), size=batch_size)
    #     rand_x = x_train[rand_index]
    #     # print(rand_x.shape)
    #     rand_y = y_train[rand_index]
    #     _, l = sess.run([train_op, loss_op], feed_dict={x: rand_x, y: rand_y})
    #     # infer_op = sess.run(infer_op)
    #     if i % 50 == 0 or i == 1:
    #         infer_op1 = sess.run(infer_op, feed_dict={x: rand_x})
    #         acc = sess.run(accuracy_op, feed_dict={x: rand_x, y: rand_y})
    #         print('Step %i, Loss: %f, Acc: %f' % (i, l, acc))
    #         yp1 = sess.run(tf.argmax(infer_op, 1), feed_dict={x: rand_x, y: rand_y})
    # saver = tf.train.Saver()
    # path = saver.save(sess, './' + f + '/rf.ckpt')
    # print('save path: {}'.format(path))

    with tf.Session() as sess:
        # Run the initializer
        sess.run(init_vars)
        for i in range(1, num_steps + 1):
            # Prepare Data
            # Get the next batch of MNIST data (only images are needed, not labels)
            # batch_x, batch_y = mnist.train.next_batch(batch_size)
            rand_index = np.random.choice(len(x_train), size=batch_size)
            rand_x = x_train[rand_index]
            # print(rand_x.shape)
            rand_y = y_train[rand_index]
            _, l = sess.run([train_op, loss_op], feed_dict={x: rand_x, y: rand_y})
            # infer_op = sess.run(infer_op)
            if i % 50 == 0 or i == 1:
                infer_op1 = sess.run(infer_op, feed_dict={x: rand_x})
                acc = sess.run(accuracy_op, feed_dict={x: rand_x, y: rand_y})
                print('Step %i, Loss: %f, Acc: %f' % (i, l, acc))
                yp1 = sess.run(tf.argmax(infer_op, 1), feed_dict={x: rand_x, y: rand_y})
        saver = tf.train.Saver()
        path = saver.save(sess, './' + f + '/rf.ckpt')
        print('save path: {}'.format(path))







def evaluate(test_index, y_label, y_score, file_name,table):
    """
    对模型的预测性能进行评估
    :param test_index
    :param y_label: 测试样本的真实标签 true label of test-set
    :param y_score: 测试样本的预测概率 predicted probability of test-set
    :param file_name: 输出文件路径    path of output file
    """
    # TODO 全部算完再写入



    # wb = xlwt.Workbook(file_name)

    table_title = ["test_index", "label", "prob", "pre", " ", "fpr", "tpr", "thresholds", " ", "fp", "tp", "fn", "tn",
                    " ","acc", "auc", "recall", "precision", "f1-score", "threshold"]
    for i in range(len(table_title)):
        table.write(0, i, table_title[i])

    # auc = roc_auc_score(y_label, y_score)
    # y_data = np.array([1 if y == 1 else -1 for y in y_score])
    y1=[]
    y2=[]
    for i in range(len(y_label)):
        if y_label[i]>0:
            y1.append([y_label[i],y_score[i]])
        else:
            y2.append([y_label[i],y_score[i]])
    count = 0

    for i in range(len(y1)):
        for j in range(len(y2)):
            if y1[i][1]>y2[j][1]:
                count = count + 1
    auc = count/(len(y1)*len(y2))
    print(auc)
    # y_pred_label = ((y_score >= 0) -0.5)*2#svm
    threshold = plot_roc(y_label, y_score, table, table_title, file_name, test_index,auc)
    y_pred_label = (y_score > threshold)*1#logical
    fp_count = 0
    tp_count = 0
    fn_count = 0
    tn_count = 0
    for j in range(len(y_label)):
        if y_label[j] == 0 and y_pred_label[j] == 1:  # FP

            fp_count += 1
        if y_label[j] == 1 and y_pred_label[j] == 1:  # TP

            tp_count += 1
        if y_label[j] == 1 and y_pred_label[j] == 0:  # FN

            fn_count += 1
        if y_label[j] == 0 and y_pred_label[j] == 0:  # TN

            tn_count += 1
    sum = fp_count + tp_count + fn_count + tn_count
    table.write(test_index+1, table_title.index("fp"), float(fp_count/sum))
    table.write(test_index+1, table_title.index("tp"), float(tp_count/sum))
    table.write(test_index+1, table_title.index("fn"), float(fn_count/sum))
    table.write(test_index+1, table_title.index("tn"), float(tn_count/sum))


    acc = (tp_count+tn_count)/sum
    recall = tp_count/(tp_count+fn_count)
    precision = tp_count/(tp_count+fp_count)
    f1 = 2*tp_count/(2*tp_count+fp_count+fn_count)

    # write metrics
    table.write(test_index+1, table_title.index("auc"), float(auc))
    table.write(test_index+1, table_title.index("acc"), float(acc))
    table.write(test_index+1, table_title.index("recall"), float(recall))
    table.write(test_index+1, table_title.index("precision"), float(precision))
    table.write(test_index+1, table_title.index("f1-score"), float(f1))


def plot_roc(test_labels, test_predictions, table, table_title, filename,test_index,a):
    """
    1.plot and save the  ROC curve with AUC value
    2.record the FPR, TPR and thresholds
    3.choose the threshold with max(TPR-FPR)
    :param test_labels: 测试集标签
    :param test_predictions: 测试集预测值
    :param table: xls文件的sheet
    :param table_title: 表头字符串数组
    :param filename: 图片文件名
    :return: optimal threshold
    """
    fpr, tpr, thresholds = roc_curve(test_labels, test_predictions, pos_label=1)
    threshold = thresholds[np.argmax(tpr - fpr)]
    for i in range(len(fpr)):
        if(a>0.54):
            table.write(i + 1, table_title.index("fpr"), fpr[i])
            table.write(i + 1, table_title.index("tpr"), tpr[i])
            table.write(i + 1, table_title.index("thresholds"), float(thresholds[i]))

    table.write(2, table_title.index("threshold"), float(threshold))
    auc = "%.3f" % sklearn.metrics.auc(fpr, tpr)
    title = 'ROC Curve, AUC = ' + str(auc)
    with plt.style.context('ggplot'):
        fig, ax = plt.subplots()
        ax.plot(fpr, tpr, "#000099", label='ROC curve')
        ax.plot([0, 1], [0, 1], 'k--', label='Baseline')
        plt.xlim([0.0, 1.0])
        plt.ylim([0.0, 1.05])
        plt.xlabel('False Positive Rate')
        plt.ylabel('True Positive Rate')
        plt.legend(loc='lower right')
        plt.title(title)
        plt.savefig(filename + '_' + str(test_index)+'.png', format='png')
        plt.close()
    return threshold


def main():
    df = pd.read_csv(open("C:/Users/dell/Desktop/luyi.csv"))  # 读取文件
    # df = pd.read_excel("C:\\Users\\Administrator\\Desktop\\book.xls")
    event_type = 'cx'

    if event_type == 'qx':
        y_data = df.values[:, 2:3]  # 读取并转化格式,缺血事件
    elif event_type == 'cx':
        y_data = df.values[:, 3:4]

    x_data = df.values[:, 4:236]  # 读取并转化格式

    y_data = y_data.astype(np.float32)

    # y_data = np.array([1 if y == 1 else -1 for y in y_data])
    x_data = x_data.astype(np.float32)




    # 5折交叉验证，重复10次
    # x_train, x_test, y_train, y_test = train_test_split( x_data, y_data, test_size=0.2, random_state=0)
    stratified_folder = StratifiedKFold(n_splits=5, random_state=0, shuffle=False)

    for i in range(10):
        k = 0
        k = 0

        file_name = 'C:/Users/dell/Desktop/RF/RF_' + event_type + '_' + str(i+1)
        wb = xlwt.Workbook(file_name + '.xls')
        table = wb.add_sheet('Sheet1', cell_overwrite_ok=True)
        for train_index, test_index in stratified_folder.split(x_data, y_data):
            f = 'rf_' + event_type + '_' + str(i + 1) + '_' + str(k + 1)
            x_train = x_data[train_index]
            x_test = x_data[test_index]
            y_train = y_data[train_index]
            y_test = y_data[test_index]
            # auc_value0[5 * i + k] = model(x_train, x_test, y_train, y_test)
            tf.reset_default_graph()
            RF_model(x_train, y_train, f)
            # -------------------if cnn----------------------------
            tf.reset_default_graph()
            sess = tf.Session()
            saver = tf.train.import_meta_graph('./' + f + '/rf.ckpt.meta')
            saver.restore(sess, tf.train.latest_checkpoint('./' + f))
            # saver = tf.train.Saver()

            # 访问placeholders变量，并且创建feed-dict来作为placeholders的新值
            graph = tf.get_default_graph()
            x = graph.get_tensor_by_name("x:0")
            y = graph.get_tensor_by_name("y:0")
            feed_dict = {x: x_test, y: y_test}
            # 接下来，访问你想要执行的op
            op_to_restore = graph.get_tensor_by_name("z:0")
            # print (sess.run(add_on_op, feed_dict))
            y_prediction = sess.run(op_to_restore, feed_dict={x: x_test})

            y_prediction = y_prediction[:,1]
            # ------------------------------------------------------
            # y_test = np.array([1 if y == 1 else 0 for y in y_test])
            evaluate(k+1, y_test, y_prediction, file_name,table)
            k = k + 1
        wb.save(file_name + '.xls')
        # print(w)
        # print(b)
        # w.append(b[0])
        # print(w)





    # print(auc_value1)

    # df = pd.read_csv(open("C:/Users/dell/Desktop/luyi.csv"))  # 读取文件
    # # df = pd.read_excel("C:\\Users\\Administrator\\Desktop\\book.xls")
    # x_data = df.values[:, 5:237]  # 读取并转化格式
    # print(x_data[0],x_data[0][0])
    # y_data = df.values[:, 3:4]  # 读取并转化格式,缺血事件
    # y_data = y_data.astype(np.float32)
    # x_data = x_data.astype(np.float32)
    # w = pd.read_excel("C:/Users/dell/Desktop/w.xlsx")
    # print(w[0][0])
    # b = 0.02390433
    #
    # for l in range(len(x_data)):
    #     py = 0
    #     for i in range(len(w)):
    #         py = py + w[0][i]*x_data[l][i]
    #
    #     py = py + b
    #
    #     print(py,y_data[l])


if __name__ == '__main__':
    main()


