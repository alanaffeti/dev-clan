<?php

namespace App\Service;

class BadWordsFilter
{
    private $badWords;

    public function __construct(string $badWordsFilePath)
    {
        $this->badWords = file($badWordsFilePath, FILE_IGNORE_NEW_LINES);
    }

    public function filter(string $text): string
    {
        $filteredText = $text;
        foreach ($this->badWords as $badWord) {
            $filteredText = preg_replace("/\b$badWord\b/i", "***", $filteredText);
        }
        return $filteredText;
    }
}
