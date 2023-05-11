<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Validator\Constraints\Regex;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
class ClienttType extends UserType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        parent::buildForm($builder, $options);
        $builder
        ->add('numTel', null, [
            'constraints' => [
                new Regex([
                    'pattern' => '/^\d{8}$/',
                    'message' => 'Le numéro de téléphone doit contenir exactement 8 chiffres.'
                ]),
            ],
        ])
            ->add('cin', null, [
                'constraints' => [
                    new Regex([
                        'pattern' => '/^[a-zA-Z]{1,2}[0-9]{3,6}$/',
                        'message' => 'Le CIN doit être une chaîne de 8 caractères, commençant par 1 ou 2 lettres suivies de 3 à 6 chiffres.'
                    ]),
                ],
            ])
            ->add('genre' ,ChoiceType::class, [
                'choices' => [
                    'Male' => 'male',
                    'Female' => 'female',
                ],
                'expanded' => true,
                'multiple' => false,
            ])
            ->add('ajouter',SubmitType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
