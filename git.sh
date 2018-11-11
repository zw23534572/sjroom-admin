# 入参1：代表项目名称
if [ ! -n "$1" ]; then
    echo "请填写提交描述"
    exit 1
else
    git add .
    git commit -m "$1"
    git push
fi