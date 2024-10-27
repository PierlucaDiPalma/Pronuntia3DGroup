package com.uniba.pronuntia;

public interface OnDataPassListener {
    void onDataPass(int points, boolean done, int numeroAiuti, int corretti, int sbagliati, String path);
}