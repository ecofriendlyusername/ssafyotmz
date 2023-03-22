from fastapi import FastAPI, File, UploadFile

from style_classifier import *
from category_classifier import *
from texture_classifier import *
from print_classifier import *
from rembg import remove

import io
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
async def remove_bg(image: UploadFile = File(...)):
    
    filename = "./dummy/test12.jpg"

    img = Image.open(filename)
    
    # img = await image.read()
    # img = io.BytesIO(img)
    # #open image
    # img = Image.open(img)
    
    #remove background
    
    img = remove(img, alpha_matting=True, alpha_matting_erode_size=15)
    
    
    #fix output
    #save image
    img.save("./remove/output.png","png")


@app.post("/ai/v1/style")
async def style_classification(image: UploadFile = File(...)):
    
    ##test
    
    filename = "./dummy/test12.jpg"

    img = Image.open(filename)
    
    """inference""" 
    # img = await image.read()
    # img = io.BytesIO(img)
    # #open image
    # img = Image.open(img).convert('RGB')
    #transformation
    if val_transform is not None:
    
        img = val_transform(img)
    
    #prepare feature
    img = img.unsqueeze(0)

    feature_var = torch.autograd.Variable(img).float()
    
    #data
    output = model_style(feature_var, inp_var)
    percentage_output = F.softmax(output, dim = 1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred,axis = 1)
    
    #result  
    result = {}
    
    for i in range(num_classes_style-1,-1,-1):
        
        result[23-i] = {"style":change_class_style[sorted_pred[0][i]],
                        "score":round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}
    
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
    percentage_output = F.softmax(output, dim = 1)


    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred,axis = 1)
    
    result = {}

    for i in range(num_classes_category-1,-1,-1):
        
        result[21-i] = {"category":change_class_category[sorted_pred[0][i]],
                        "score":round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}
        
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
    percentage_output = F.softmax(output, dim = 1)


    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred,axis = 1)
    
    result = {}

    for i in range(num_classes_texture-1,-1,-1):
        
        result[27-i] = {"texture":change_class_texture[sorted_pred[0][i]],
                        "score":round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}
        
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
    percentage_output = F.softmax(output, dim = 1)


    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred,axis = 1)
    
    result = {}

    for i in range(num_classes_print-1,-1,-1):
        
        result[21-i] = {"print":change_class_print[sorted_pred[0][i]],
                        "score":round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}
        
    return result