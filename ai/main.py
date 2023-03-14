from fastapi import FastAPI
from style_classifier import *

app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}

@app.get("/ai/v1/style/{img}")
async def style_classification(img: str):
    """inference"""
    
    img = "./test/64471.jpg"
    
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