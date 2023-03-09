<?php

namespace App\Twig;

use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class FileExtension extends AbstractExtension
{
    public function getFunctions(): array
    {
        return [
            new TwigFunction('file', [$this, 'getFileContents']),
        ];
    }

    public function getFileContents(string $filename): string
    {
        return file_get_contents($filename);
    }
}