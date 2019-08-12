# CODE CHALLENGE

---

### Summary

This project was created through of the GrupoZap's Code Challenge.

### Instructions to setup the project

To start the project, you need the following tools installed:

1. **Gradle 4.10 or Higher**
2. **Java 8**
3. **Docker and Docker Compose** (Optional) to start the container from image.
    * If you want following this way, below I described how to do.
4. **Test API using JUnit** 

### How to test the project

- By gradle at terminal, put the property **-i** to see log.

```renderscript
   gradle test -i    
```

- By Intellij
    - Go to File > Settings > Plugins
    - Click on Browse repositories...
    - Search for Lombok Plugin
    - Click on Install plugin
    - Restart IntelliJ IDEA
    
    > Go to File > Setting > Build, Execution, Deployment > Annotation Processors 
    
    > Now you need click **Enable annotation processing**
    

- By Eclipse
    - Following the step-by-step [Lombok Eclipse Setup](https://projectlombok.org/setup/eclipse)


### How to run the project

##### If you want to use the docker, execute the command below

- Downloading the image

```renderscript
    docker pull eltonsimor/code-challenge
```
- Executing the image

```renderscript
    docker run -it --dns 8.8.8.8 --rm -p 8080:8080 eltonsimor/code-challenge    
```

- Executing on docker-compose <- (You need to do download the image before)

```renderscript
    docker-compose up
```
> PS: To execute on Linux, you need superuser. Execute above with **sudo su**

> If you want to access the **Hub Docker**, just click **[HERE](https://hub.docker.com/r/eltonsimor/code-challenge/)**

##### Executing on gradle command

Just run the command below.

```renderscript
    gradle bootRun
```

##### Executing on kubernetes

- Applying Service Command:

```renderscript
kubectl apply -f k8s/code-challenge-service.json
```
- Applying Deployment Command:
    
```renderscript
kubectl apply -f k8s/code-challenge-deployment.json
```

### Services to Rent and Sales Properties 

- Getting all properties by broker.

```javascript
GET: /code-challenge/properties?pageNumber={pageNumber}&pageSize={pageSize}&broker={broker} <- {VIVAREAL, GRUPOZAP, NOT_ELEGIBLE}
Status 200 OK
```
> The parameters **pageNumber** and **pageSize** by default have value defined. **(pageNumber = 1 and pageSize = 100)**

##### Response

```json
{
   "pageNumber":1,
   "pageSize":2,
   "totalCount":2,
   "listings":[
      {
         "usableAreas":69,
         "listingType":"USED",
         "createdAt":"2016-11-16T04:14:02Z",
         "listingStatus":"ACTIVE",
         "id":"a0f9d9647551",
         "parkingSpaces":1,
         "updatedAt":"2016-11-16T04:14:02Z",
         "owner":false,
         "images":[
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/285805119ab0761500127aebd8ab0e1d.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/4af1656b66b9e12efff6ce06f51926f6.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/895f0d4ce1e641fd5c3aad48eff83ac8.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/e7b5cce2d9aee78867328dfa0a7ba4c6.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/d833da4cdf6b25b7acf3ae0710d3286d.jpg"
         ],
         "address":{
            "city":"",
            "neighborhood":"",
            "geoLocation":{
               "precision":"ROOFTOP",
               "location":{
                  "lon":-46.716542,
                  "lat":-23.502555
               }
            }
         },
         "bathrooms":2,
         "bedrooms":3,
         "pricingInfos":{
            "yearlyIptu":"0",
            "price":"405000",
            "businessType":"SALE",
            "monthlyCondoFee":"495"
         }
      },
      {
         "usableAreas":70,
         "listingType":"USED",
         "createdAt":"2017-04-22T18:39:31.138Z",
         "listingStatus":"ACTIVE",
         "id":"7baf2775d4a2",
         "parkingSpaces":1,
         "updatedAt":"2017-04-22T18:39:31.138Z",
         "owner":false,
         "images":[
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/f908948bf1d9e53d36c4734d296feb99.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/bd37e4c09ddd370529489fbbc461dbec.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/7b86204f34b751c432c878d607c9d618.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/3b1142cffc13c49a1e7ea766c20a1d52.jpg",
            "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/f519a27d56696febfb77f6398f4484d8.jpg"
         ],
         "address":{
            "city":"",
            "neighborhood":"",
            "geoLocation":{
               "precision":"NO_GEOCODE",
               "location":{
                  "lon":0,
                  "lat":0
               }
            }
         },
         "bathrooms":1,
         "bedrooms":2,
         "pricingInfos":{
            "yearlyIptu":"60",
            "price":"276000",
            "businessType":"SALE",
            "monthlyCondoFee":"0"
         }
      }
   ]
}
```

- Getting property by id.

```javascript
GET /code-challenge/properties/{id}
Status 200 OK
```

##### Response

```json
{
   "usableAreas":69,
   "listingType":"USED",
   "createdAt":"2016-11-16T04:14:02Z",
   "listingStatus":"ACTIVE",
   "id":"a0f9d9647551",
   "parkingSpaces":1,
   "updatedAt":"2016-11-16T04:14:02Z",
   "owner":false,
   "images":[
      "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/285805119ab0761500127aebd8ab0e1d.jpg",
      "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/4af1656b66b9e12efff6ce06f51926f6.jpg",
      "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/895f0d4ce1e641fd5c3aad48eff83ac8.jpg",
      "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/e7b5cce2d9aee78867328dfa0a7ba4c6.jpg",
      "https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/d833da4cdf6b25b7acf3ae0710d3286d.jpg"
   ],
   "address":{
      "city":"",
      "neighborhood":"",
      "geoLocation":{
         "precision":"ROOFTOP",
         "location":{
            "lon":-46.716542,
            "lat":-23.502555
         }
      }
   },
   "bathrooms":2,
   "bedrooms":3,
   "pricingInfos":{
      "yearlyIptu":"0",
      "price":"405000",
      "businessType":"SALE",
      "monthlyCondoFee":"495"
   }
}
```