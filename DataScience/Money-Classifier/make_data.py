import numpy as np
import cv2
import time
import os

# Label: 00000 means no money held, others are denominations
label = "00000"

cap = cv2.VideoCapture(0)

# Counter variable to only save data after about 60 frames, avoiding saving before holding money
i=0
while(True):
    # Capture frame-by-frame
    #
    i+=1
    ret, frame = cap.read()
    if not ret:
        continue
    frame = cv2.resize(frame, dsize=None,fx=0.3,fy=0.3)

    # Display
    cv2.imshow('frame',frame)

    # Save data
    if i>=60:
        print("Number of captured images = ",i-60)
        # Create directory if it doesn't exist
        if not os.path.exists('data/' + str(label)):
            os.makedirs('data/' + str(label))

        cv2.imwrite('data/' + str(label) + "/" + str(i) + ".png",frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()