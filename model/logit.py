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

# 建模：tensorflow框架 逻辑回归方法
def logical_model(x_train, x_test, y_train, y_test):
    # 定义
    # feature_num = 232
    # sample_num =2390
    sample_num, feature_num = x_train.shape

    x = tf.placeholder(tf.float32)  # 占位
    y = tf.placeholder(tf.float32)
    # 定义W：232*2，b
    W = tf.Variable(tf.random_uniform([feature_num, 1], -1.0, 1.0))
    b = tf.Variable(tf.random_normal(shape=[1,1]))
    db = tf.matmul(x, W) + b  # db=W*x+b
    hyp = tf.sigmoid(db)  # hyp=1/(1+e^-db)

    cost0 = y * tf.log(hyp)
    cost1 = (1 - y) * tf.log(1 - hyp)
    cost2 = (cost0 + cost1) / -sample_num
    cost3 = 100 * (W ** 2) / (sample_num * 2)  # 正则化L2=1
    loss = tf.reduce_sum(cost2) + tf.reduce_sum(cost3)
    optimizer = tf.train.GradientDescentOptimizer(0.01)  # 梯度下降步长设为0.005
    train = optimizer.minimize(loss)  # 最小化误差
    # 初始化变量
    init = tf.global_variables_initializer()
    sess = tf.Session()
    sess.run(init)

    feed_dict = {x: x_train, y: y_train}  # 字典
    # 开始训练60000次，每隔100次输出W，b
    for step in range(20000):
        sess.run(train, {x: x_train, y: y_train})
        # if step % 10000 == 0:
        #     # print(step, sess.run(W).flatten(), sess.run(b).flatten())
        #     print(step, sess.run(W), sess.run(b))

    # 保存W，b并计算y
    w_value = sess.run(W)
    b_value = sess.run(b)
    y_prediction = np.dot(x_test, w_value) + b_value
    y_prediction = 1 / (1 + np.exp(-1 * y_prediction))


    """for k ,v in enumerate(y_prediction):
        if v<0:
            y_prediction[k]=0
        else:
            y_prediction[k]=1
       """
    # yp=1/(1+np.exp(-1*y_prediction))


    #     # print(roc_auc_score(y_test.T,y_prediction.T))
    # print(roc_auc_score(y_test.T[1],y_prediction.T[1]))
    # evaluate(index,y_test,y_prediction1,'C:/Users/dell/Desktop/r')
    return  w_value, b_value , y_prediction#  # ,roc_auc_score(y_test.T[1],y_prediction.T[1])


"""
    fpr, tpr, tresholds = roc_curve(y_data[0], y_prediction[0])
    print(auc(fpr, tpr, reorder=True))

    roc_auc = auc(fpr, tpr)
    #画图，只需要plt.plot(fpr,tpr),变量roc_auc只是记录auc的值，通过auc()函数能计算出来
    plt.plot(fpr, tpr, lw=1, label='ROC fold %d (area = %0.2f)' % (i, roc_auc))
    #画对角线
    plt.plot([0, 1], [0, 1], '--', color=(0.6, 0.6, 0.6), label='Luck')
    plt.show()
"""

def svm_model(x_train, x_test, y_train, y_test):


    sample_num, feature_num = x_train.shape

    # 批训练中批的大小
    batch_size = 1000
    x = tf.placeholder(tf.float32)  # 占位
    y = tf.placeholder(tf.float32)
    # 定义W：232*2，b
    W = tf.Variable(tf.random_uniform([feature_num, 1], -10,10))
    b = tf.Variable(tf.random_normal(shape=[1, 1]))
    # 定义损失函数
    model_output = tf.matmul(x, W) + b
    l2_norm = tf.reduce_sum(tf.square(W))
    # 软正则化参数
    alpha = 0.1
    # 定义损失函数
    classification_term = tf.reduce_mean(tf.maximum(0., 1. - model_output * y))
    loss = classification_term + alpha * l2_norm
    # 输出
    prediction = tf.sign(model_output)
    # accuracy = tf.reduce_mean(tf.cast(tf.equal(prediction, y_target), tf.float32))
    train_step = tf.train.GradientDescentOptimizer(0.01).minimize(loss)
    # auc = roc_auc_score(prediction, y_target)
    # 开始训练
    sess = tf.Session()
    sess.run(tf.global_variables_initializer())
    feed_dict = {x: x_train, y: y_train}
    # loss_vec = []
    # train_accuracy = []
    # test_accuracy = []
    # auc_line = []
    for i in range(500):
        # z,r = sess.run([train_step,model_output], {x: x_train, y: y_train})
        rand_index = np.random.choice(len(x_train), size=batch_size)
        rand_x = x_train[rand_index]
        rand_y = np.transpose([y_train[rand_index]])
        _, o, l = sess.run([train_step, model_output, classification_term], feed_dict={x: rand_x, y: rand_y})
        # temp_loss = sess.run(loss, feed_dict={x_data: rand_x, y_target: rand_y})//
        # loss_vec.append(temp_loss)
        # train_acc_temp = sess.run(accuracy, feed_dict={x_data: x_vals_train, y_target: np.transpose([y_vals_train])})
        # train_accuracy.append(train_acc_temp)
        # test_acc_temp = sess.run(accuracy, feed_dict={x_data: x_vals_test, y_target: np.transpose([y_vals_test])})
        # test_accuracy.append(test_acc_temp)
        # test_auc = sess.run(auc, feed_dict={x_data: x_vals_test, y_target: np.transpose([y_vals_test])})
        # auc_line.append(test_auc)

    # 保存W，b并计算y
    w_value = sess.run(W)
    b_value = sess.run(b)
    y_prediction = np.dot(x_test, w_value) + b_value
    y_prediction1 = sess.run(prediction, feed_dict={x: x_test})
    yp = abs(y_prediction)
    # y_prediction = 1 / (1 + np.exp(-1 * y_prediction))



    """for k ,v in enumerate(y_prediction):
        if v<0:
            y_prediction[k]=0
        else:
            y_prediction[k]=1
       """
    # yp=1/(1+np.exp(-1*y_prediction))


    #     # print(roc_auc_score(y_test.T,y_prediction.T))
    # print(roc_auc_score(y_test.T[1],y_prediction.T[1]))
    # evaluate(index,y_test,y_prediction1,'C:/Users/dell/Desktop/r')
    return  w_value, b_value , y_prediction#  # ,roc_auc_score(y_test.T[1],y_prediction.T[1])




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

    # y_data2 = []
    # for y in y_score:
    #     if y == 1:
    #         y_data2.append([1])
    #     else:
    #         y_data2.append([-1])
    # y_score = y_data2
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
    print("auc:" + str(auc))
    y_pred_label = ((y_score >= 0) -0.5)*2#svm
    threshold = plot_roc(y_label, y_score, table, table_title, file_name, test_index,auc)
    # y_pred_label = (y_score > threshold)*1#logical
    fp_count = 0
    tp_count = 0
    fn_count = 0
    tn_count = 0
    for j in range(len(y_label)):
        if y_label[j] == -1 and y_pred_label[j] == 1:  # FP

            fp_count += 1
        if y_label[j] == 1 and y_pred_label[j] == 1:  # TP

            tp_count += 1
        if y_label[j] == 1 and y_pred_label[j] == -1:  # FN

            fn_count += 1
        if y_label[j] == -1 and y_pred_label[j] == -1:  # TN

            tn_count += 1
    sum = fp_count + tp_count + fn_count + tn_count
    table.write(test_index+1, table_title.index("fp"), float(fp_count/sum))
    table.write(test_index+1, table_title.index("tp"), float(tp_count/sum))
    table.write(test_index+1, table_title.index("fn"), float(fn_count/sum))
    table.write(test_index+1, table_title.index("tn"), float(tn_count/sum))


    acc = (tp_count+tn_count)/sum
    recall = tp_count/(tp_count+fn_count)
    if(tp_count+fp_count>0):
        precision = tp_count/(tp_count+fp_count)
    else:
        precision = 0
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
        if (a > 0.54 and a<0.6):
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


    auc_value0 = [0 for i in range(50)]

    # 5折交叉验证，重复10次
    # x_train, x_test, y_train, y_test = train_test_split( x_data, y_data, test_size=0.2, random_state=0)
    stratified_folder = StratifiedKFold(n_splits=5, random_state=0, shuffle=False)

    for i in range(10):
        k = 0
        file_name = 'C:/Users/dell/Desktop/svm/svm_' + event_type + '_' + str(i+1)
        wb = xlwt.Workbook(file_name + '.xls')
        table = wb.add_sheet('Sheet1', cell_overwrite_ok=True)
        for train_index, test_index in stratified_folder.split(x_data, y_data):
            x_train = x_data[train_index]
            x_test = x_data[test_index]
            y_train = y_data[train_index]
            y_test = y_data[test_index]
            # auc_value0[5 * i + k] = model(x_train, x_test, y_train, y_test)
            w,b,y_prediction = svm_model(x_train, x_test, y_train, y_test)
            # y_test = np.array([1 if y == 1 else 0 for y in y_test])
            evaluate(k+1, y_test, y_prediction, file_name,table)
            k = k + 1
        wb.save(file_name + '.xls')
        # print(w)
        # print(b)
        # w.append(b[0])
        # print(w)
        W2 = w
        w = pd.DataFrame(W2)
        # print(w)
        w['b']=None
        w['b'][0]=b[0][0]
        # print(w)
        file_name2 = "C:/Users/dell/Desktop/svm/canshu_" + event_type + str(i + 1) + ".xlsx"
        w.to_excel(file_name2)




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


