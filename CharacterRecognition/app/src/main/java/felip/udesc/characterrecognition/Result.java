package felip.udesc.characterrecognition;

class Result {

    private final int result;
    private final float probability;

    Result(float[] probs) {
        result = argmax(probs);
        probability = probs[result];
    }

    int getResult() {
        return result;
    }

    public float getProbability() {
        return probability;
    }

    private static int argmax(float[] probs) {
        int maxIdx = -1;
        float maxProb = 0.0f;
        for (int i = 0; i < probs.length; i++) {
            if (probs[i] > maxProb) {
                maxProb = probs[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}