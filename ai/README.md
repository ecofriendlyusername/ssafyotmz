1. 가상환경 설치

python -m venv venv

2. 가상환경 실행

source venv/Scripts/activate

3. 파이썬 버전 지정

py -3.8 -m venv venv

4. 패키지 설치

pip install -r req.txt

5. 서버 실행
uvicorn main:app --reload