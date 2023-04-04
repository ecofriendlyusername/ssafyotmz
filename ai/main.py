from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse

from style_classifier import *
from single_classifier import *
from object_detection import *

from category_classifier import *
from texture_classifier import *
from print_classifier import *
from rembg import remove

import io
import base64

from starlette.middleware.cors import CORSMiddleware

origins = [
    "http://127.0.0.1",
    "http://127.0.0.1:3000",
    "http://localhost:3000"
]

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

def from_image_to_bytes(img):
    """
    pillow image 객체를 bytes로 변환
    """
    # Pillow 이미지 객체를 Bytes로 변환
    imgByteArr = io.BytesIO()
    img.save(imgByteArr, format=img.format)
    imgByteArr = imgByteArr.getvalue()
    # Base64로 Bytes를 인코딩
    encoded = base64.b64encode(imgByteArr)
    # Base64로 ascii로 디코딩
    decoded = encoded.decode('ascii')
    return decoded

def detection_clothes(img):
    
    hide_labels = False 
    hide_conf = False 

    img_size = 640

    conf_thres =.25
    iou_thres =.45
    max_det =  1000
    agnostic_nms = False

    img_size = check_img_size(img_size, s=stride)

    img, img_src = process_image(img, img_size, stride, half)
    img = img.to(device)
    if len(img.shape) == 3:
        img = img[None]
        # expand for batch dim
    pred_results = model_detect(img)
    classes = [0,1,2,3] # the classes to keep
    det = non_max_suppression(pred_results, conf_thres, iou_thres, classes, agnostic_nms, max_det=max_det)[0]
    
    find = False
    
    for d in det:
        
        if int(d[5].item()) == 0:
            
            find = True
            break
    
    return find


@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}


@app.get("/test")
async def test(img: UploadFile = File(...)):
    return {"filename": img.filename}


@app.post("/ai/v1/remove")
async def remove_bg_and_style_classification(image: UploadFile = File(...)):
    
    #read image data
    img = await image.read()
    img = io.BytesIO(img)
    #open image
    img = Image.open(img)
    
    ##########################################################
    #inference style
    
    inf_img = img.convert('RGB')
    # transformation
    if val_transform is not None:

        inf_img = val_transform(inf_img)
    
    # prepare feature
    inf_img = inf_img.unsqueeze(0)

    feature_var = torch.autograd.Variable(inf_img).float()
    
    # data
    output = model_single(feature_var, inp_var)
    percentage_output = F.softmax(output, dim=1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred, axis=1)

    # result
    result = {}

    for i in range(num_classes_style-1, -1, -1):

        result[23-i] = {"style": change_class_style[sorted_pred[0][i]],
                        "score": round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}
    
    
    ###############################################################################
    # remove background
    img = remove(img, alpha_matting=True, alpha_matting_erode_size=15)
    
    # fix output
    # save image
    x = int(round(np.random.rand(),5)*(10**5))
    
    img.save(f"./remove/{x}.png", "png")
    
    img = Image.open(f"./remove/{x}.png")
    
    img_converted = from_image_to_bytes(img)
    
    return {"image":JSONResponse(img_converted),"style":result}
    
    


@app.post("/ai/v1/style")
async def style_classification(imageFile: UploadFile = File(...)):

    #filename = "./dummy/test12.jpg"

    #img = Image.open(filename).convert('RGB')

    """inference"""
    img = await imageFile.read()
    img = io.BytesIO(img)
    # open image
    img = Image.open(img).convert('RGB')
    
    if detection_clothes(img) == False:
        
        return {
            'check': False,
            'data': {}
        }
    
    # transformation
    if val_transform is not None:

        img = val_transform(img)

    # prepare feature
    img = img.unsqueeze(0)

    feature_var = torch.autograd.Variable(img).float()

    # data
    output = model_style(feature_var, inp_var)
    percentage_output = F.softmax(output, dim=1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred, axis=1)

    # result
    result = {'check': True,
              'data': {}}

    for i in range(num_classes_style-1, -1, -1):
        result['data'][23-i] = {"style": change_class_style[sorted_pred[0][i]],
                        "score": round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}

    return result


@app.post("/ai/v1/category")
async def category_classification(image: UploadFile = File(...)):
    """#inference"""

    filename = "./dummy/test_skirt.jpg"

    img = Image.open(filename).convert('RGB')

    # img = await image.read()
    # img = io.BytesIO(img)
    # #open image
    # img = Image.open(img).convert('RGB')

    if val_transform is not None:

        img = val_transform(img)

    img = img.unsqueeze(0)

    feature_var = torch.autograd.Variable(img).float()

    output = model_category(feature_var)
    percentage_output = F.softmax(output, dim=1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred, axis=1)

    result = {}

    for i in range(num_classes_category-1, -1, -1):

        result[21-i] = {"category": change_class_category[sorted_pred[0][i]],
                        "score": round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}

    return result


@app.post("/ai/v1/texture")
async def texture_classification(image: UploadFile = File(...)):
    """#inference"""

    filename = "./dummy/test_skirt.jpg"

    img = Image.open(filename).convert('RGB')

    # img = await image.read()
    # img = io.BytesIO(img)
    # #open image
    # img = Image.open(img).convert('RGB')

    if val_transform is not None:

        img = val_transform(img)

    img = img.unsqueeze(0)

    feature_var = torch.autograd.Variable(img).float()

    output = model_texture(feature_var)
    percentage_output = F.softmax(output, dim=1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred, axis=1)

    result = {}

    for i in range(num_classes_texture-1, -1, -1):

        result[27-i] = {"texture": change_class_texture[sorted_pred[0][i]],
                        "score": round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}

    return result


@app.post("/ai/v1/print")
async def print_classification(image: UploadFile = File(...)):
    """#inference"""

    filename = "./dummy/test_print.JPG"

    img = Image.open(filename).convert('RGB')

    # img = await image.read()
    # img = io.BytesIO(img)
    # #open image
    # img = Image.open(img).convert('RGB')

    if val_transform is not None:

        img = val_transform(img)

    img = img.unsqueeze(0)

    feature_var = torch.autograd.Variable(img).float()

    output = model_print(feature_var)
    percentage_output = F.softmax(output, dim=1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred, axis=1)

    result = {}

    for i in range(num_classes_print-1, -1, -1):

        result[21-i] = {"print": change_class_print[sorted_pred[0][i]],
                        "score": round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}

    return result
