# Recycle Buddy
### By Rebecca Martino, Patrick Sacchet and Chelsea Meier
#### HoyaHacks Spring 2020

## Premise
In working to tackle the challenge of getting more people to compost and recycle, we realized many are not well educated on the types of materials that are compostable / recyclable. In creating Recycle Buddy, we aimed to create a simple and easy interaction between our users and the information available on everyday foods so we could streamline this knowledge, making it that much easier to make a positve impact on our enviornment and our users. Recycle Buddy uses publicly available information detailing the sepcifics of everyday food items to inform our users what exactly they're throwing away; additionally, Recycle Buddy implements a secured online database webhosting platform that allowed us to keep our users' information and accounts secured. This provided a way to authenticate and better track our users upon login.

## What We Used:
### USDA FoodData Central Database
FoodData Central is an integrated data system that provides expanded nutrient profile data and links to related agricultural and experimental research. This information is queried and gathereed to the user upon searching for a food item. With a basic breakdown of some of the nutritional available for most food products, we are put in a position where it would be fairly simple to implement the recycable / compostable functionality given we are provided the proper API.

### Earth911
Earth911 is the API we originally intended on implementing into our app to simply display the composte / recycle options that are available to users, dependent on the type of product it is. Sadly, with their API being kept behind locked doors, we were unable to gain access to an API key, meaning this last bit of functionality is incomplete. We hope once we gain access we will be able to simply add the information for the food item along with the information we already have about the specifics of each product. 

### MongoDB / MongoDB Stitch
Mongo DB served as our primary tool in successfuly adding and validating users upon login. Their platform allows for a simple implementation of security while also remaining extendable for future growth of users. Additionally, with the implementation of MongoDB Stitch, we were able to deploy an application that could successfully interact with our application being hosted on Android devices. 

## Reflection
In looking towards the future, we hope to gain access to Earth911 API that will allow us to implement the recyclability / compostability aspect we can use to inform our users. Overall, we have learned a lot as a team when it comes to online database platform hosting, android applications and accoutn security and storage. 



