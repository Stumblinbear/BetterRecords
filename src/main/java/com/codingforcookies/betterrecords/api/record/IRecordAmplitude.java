package com.codingforcookies.betterrecords.api.record;

public interface IRecordAmplitude {
    void setTreble(float amplitude);
    void setTreble(float amplitude, float r, float g, float b);
    float getTreble();

    void setBass(float amplitude);
    void setBass(float amplitude, float r, float g, float b);
    float getBass();
}
