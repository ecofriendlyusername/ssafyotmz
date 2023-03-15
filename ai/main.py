from fastapi import FastAPI, File, UploadFile
from style_classifier import *
import io

app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}


@app.get("/test")
async def test(img: UploadFile = File(...)):
    return {"filename": img.filename}


@app.get("/ai/v1/style")
async def style_classification(image: UploadFile = File(...)):
    """inference""" 
    img = await image.read()
    img = io.BytesIO(img)
    #open image
    img = Image.open(img).convert('RGB')
    #transformation
    if val_transform is not None:
    
        img = val_transform(img)
    
    #prepare feature
    img = img.unsqueeze(0)

    feature_var = torch.autograd.Variable(img).float()
    
    #eval mode

    model.eval()
    
    #data
    output = model(feature_var, inp_var)
    percentage_output = F.softmax(output, dim = 1)

    pred = output.cpu().detach().numpy()

    sorted_pred = np.argsort(pred,axis = 1)
    
    #result  
    result = {}
    
    for i in range(num_classes-1,-1,-1):
        
        result[10-i] = {"style":change_category[sorted_pred[0][i]],
                        "score":round((percentage_output[0][sorted_pred[0][i]].item())*100, 4)}
    
    return result